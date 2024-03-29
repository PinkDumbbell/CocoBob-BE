package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.auth.dto.AppleOauthResponse;
import com.pinkdumbell.cocobob.domain.auth.dto.ApplePublicKeysResponse;
import com.pinkdumbell.cocobob.domain.auth.dto.DecodedIdTokenAndRefreshTokenDto;
import com.pinkdumbell.cocobob.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
public class AppleUtil {

    private final AppleOauthInfo appleOauthInfo;
    private final AppleRefreshTokenRepository appleRefreshTokenRepository;
    @Value("${apple.key.path}")
    private String privateKeyPath;

    public DecodedIdTokenAndRefreshTokenDto getEmailFromIdToken(String code) {
        return decodeIdToken(doPost(code));
    }

    @Transactional
    public void saveOrUpdateRefreshToken(User user, String refreshToken) {
        Optional<AppleRefreshToken> foundAppleRefreshToken = appleRefreshTokenRepository.findById(user.getId());
        if (foundAppleRefreshToken.isEmpty()) {
            appleRefreshTokenRepository.save(
                    AppleRefreshToken.builder()
                            .user(user)
                            .value(refreshToken)
                            .build()
            );
        } else {
            foundAppleRefreshToken.get().update(refreshToken);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Optional<AppleRefreshToken> getAppleRefreshToken(Long userId) {
        return appleRefreshTokenRepository.findById(userId);
    }

    public void revoke(AppleRefreshToken appleRefreshToken) {
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("client_id", appleOauthInfo.getClientId());
        request.add("client_secret", createClientSecret());
        request.add("token", appleRefreshToken.getValue());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(request, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.postForEntity(
                appleOauthInfo.getAppleAuthUrl() + "/auth/revoke",
                httpEntity,
                Object.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("애플 계정 탈퇴를 실패하였습니다.");
        }
    }

    private String createClientSecret() {

        Map<String, Object> header = new HashMap<>();
        header.put("kid", appleOauthInfo.getKeyId());
        header.put("alg", "ES256");

        return Jwts.builder()
                .setHeaderParams(header)
                .setIssuer(appleOauthInfo.getTeamId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000000))
                .setAudience(appleOauthInfo.getAppleAuthUrl())
                .setSubject(appleOauthInfo.getClientId())
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {

        try {
            ClassPathResource resource = new ClassPathResource(privateKeyPath);
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            Stream<String> stringStream = new BufferedReader(inputStreamReader).lines();
            List<String> collect = stringStream.map(s -> s + '\n').collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (String s : collect) {
                sb.append(s);
            }
            String lines = new String(sb);
            String privateKey = lines.substring(0, lines.length() - 1);
            Reader reader = new StringReader(privateKey);
            PEMParser pemParser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemParser.readObject();

            return converter.getPrivateKey(privateKeyInfo);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Class Name : " + Thread.currentThread().getStackTrace()[1].getClassName() + "\n" +
                    "Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName()
            );
        }

    }

    private AppleOauthResponse doPost(String code) {

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> appleOauthRequest = new LinkedMultiValueMap<>();
        appleOauthRequest.add("client_id", appleOauthInfo.getClientId());
        appleOauthRequest.add("client_secret", createClientSecret());
        appleOauthRequest.add("code", code);
        appleOauthRequest.add("grant_type", "authorization_code");
        appleOauthRequest.add("redirect_uri", appleOauthInfo.getRedirectUri());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        HttpEntity<MultiValueMap<String, String>> appleOauthRequestHttpEntity = new HttpEntity<>(appleOauthRequest, httpHeaders);
        ResponseEntity<AppleOauthResponse> appleOauthResponseResponseEntity = restTemplate.postForEntity(
                appleOauthInfo.getAppleAuthUrl() + "/auth/token",
                appleOauthRequestHttpEntity,
                AppleOauthResponse.class);

        if (!appleOauthResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(
                    "Class Name : " + Thread.currentThread().getStackTrace()[1].getClassName() + "\n" +
                    "Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName()
            );
        }

        return appleOauthResponseResponseEntity.getBody();
    }

    private DecodedIdTokenAndRefreshTokenDto decodeIdToken(AppleOauthResponse appleOauthResponse) {
        String idToken = appleOauthResponse.getId_token();
        PublicKey publicKey = createPublicKey(getProperPublicKey(idToken));
        Claims decodedIdToken = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken).getBody();

        return new DecodedIdTokenAndRefreshTokenDto(
                new JSONObject(decodedIdToken),
                appleOauthResponse.getRefresh_token()
        );
    }

    private ApplePublicKeysResponse getPublicKeys() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApplePublicKeysResponse> response = restTemplate.getForEntity(appleOauthInfo.getAppleAuthUrl() + "/auth/keys", ApplePublicKeysResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(
                    "Class Name : " + Thread.currentThread().getStackTrace()[1].getClassName() + "\n" +
                    "Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName()
            );
        }

        return response.getBody();
    }

    private ApplePublicKeysResponse.Key getProperPublicKey(String idToken) {

        try {
            String[] split = idToken.split("[.]");
            String headerString = new String(Base64.getDecoder().decode(split[0]));
            JSONParser jsonParser = new JSONParser();
            JSONObject header = (JSONObject) jsonParser.parse(headerString);
            ApplePublicKeysResponse.Key properPublicKey = null;
            for (ApplePublicKeysResponse.Key key : getPublicKeys().getKeys()) {
                if (key.getKid().equals(header.get("kid").toString()) && key.getAlg().equals(header.get("alg").toString())) {
                    properPublicKey = key;
                    break;
                }
            }
            return properPublicKey;
        } catch (ParseException e) {
            throw new RuntimeException(
                    "Class Name : " + Thread.currentThread().getStackTrace()[1].getClassName() + "\n" +
                    "Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName()
            );
        }
    }

    private PublicKey createPublicKey(ApplePublicKeysResponse.Key key) {
        String n = key.getN();
        String e = key.getE();

        byte[] nBytes = Base64.getUrlDecoder().decode(n);
        byte[] eBytes = Base64.getUrlDecoder().decode(e);

        BigInteger ni = new BigInteger(1, nBytes);
        BigInteger ei = new BigInteger(1, eBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(ni, ei);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(
                    "Class Name : " + Thread.currentThread().getStackTrace()[1].getClassName() + "\n" +
                    "Method Name : " + Thread.currentThread().getStackTrace()[1].getMethodName()
            );
        }
    }
}
