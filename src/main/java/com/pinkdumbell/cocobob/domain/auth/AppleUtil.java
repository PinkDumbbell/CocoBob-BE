package com.pinkdumbell.cocobob.domain.auth;

import com.pinkdumbell.cocobob.domain.auth.dto.AppleOauthRequest;
import com.pinkdumbell.cocobob.domain.auth.dto.AppleOauthResponse;
import com.pinkdumbell.cocobob.domain.auth.dto.ApplePublicKeysResponse;
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
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    @Value("${apple.key.path}")
    private String privateKeyPath;

    public String getAppleOauthLoginUrl() {

        return appleOauthInfo.getAppleAuthUrl() +
                "/auth/authorize?response_type=code&client_id=" +
                appleOauthInfo.getClientId() + "&redirect_uri=" + appleOauthInfo.getRedirectUri() +
                "&scope=name%20email&response_mode=form_post";
    }

    public String getEmailFromIdToken(String code) {
        JSONObject userInfo = decodeIdToken(doPost(code));
        try {
            return userInfo.get("email").toString();
        } catch (NullPointerException e) {
            System.out.println("클래스명 : "+Thread.currentThread().getStackTrace()[1].getClassName());
            System.out.println("메소드명 : "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new RuntimeException();
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
            System.out.println("========================================");
            System.out.println("privateKey exists ? " + resource.exists());
            System.out.println("========================================");
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            Stream<String> stringStream = new BufferedReader(inputStreamReader).lines();
//            String privateKey = stringStream.collect(Collectors.joining());
            List<String> collect = stringStream.map(s -> s + '\n').collect(Collectors.toList());
            StringBuilder sb = new StringBuilder();
            for (String s : collect) {
                sb.append(s);
            }
            String lines = new String(sb);
            String privateKey = lines.substring(0, lines.length() - 1);
//            String privateKey = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            Reader reader = new StringReader(privateKey);
            PEMParser pemParser = new PEMParser(reader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) pemParser.readObject();

            return converter.getPrivateKey(privateKeyInfo);
        } catch (IOException e) {
            System.out.println("클래스명 : "+Thread.currentThread().getStackTrace()[1].getClassName());
            System.out.println("메소드명 : "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new RuntimeException(e);
        }

    }

    private AppleOauthResponse doPost(String code) {

        RestTemplate restTemplate = new RestTemplate();
        AppleOauthRequest appleOauthRequest = AppleOauthRequest.builder()
                .client_id(appleOauthInfo.getClientId())
                .client_secret(createClientSecret())
                .code(code)
                .grant_type("authorization_code")
                .redirect_uri(appleOauthInfo.getRedirectUri())
                .build();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        HttpEntity<AppleOauthRequest> appleOauthRequestHttpEntity = new HttpEntity<>(appleOauthRequest, httpHeaders);
        ResponseEntity<AppleOauthResponse> appleOauthResponseResponseEntity = restTemplate.postForEntity(
                appleOauthInfo.getAppleAuthUrl() + "/auth/token",
                appleOauthRequestHttpEntity,
                AppleOauthResponse.class);

        if (!appleOauthResponseResponseEntity.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("");
        }

        return appleOauthResponseResponseEntity.getBody();
    }

    private JSONObject decodeIdToken(AppleOauthResponse appleOauthResponse) {
        String idToken = appleOauthResponse.getId_token();
        PublicKey publicKey = createPublicKey(getProperPublicKey(idToken));
        Claims decodedIdToken = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(idToken).getBody();

        return new JSONObject(decodedIdToken);
    }

    private ApplePublicKeysResponse getPublicKeys() {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ApplePublicKeysResponse> response = restTemplate.getForEntity(appleOauthInfo.getAppleAuthUrl() + "/keys", ApplePublicKeysResponse.class);
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("");
        }

        return response.getBody();
    }

    private ApplePublicKeysResponse.Key getProperPublicKey(String idToken) {

        try {
            String[] split = idToken.split("[.]");
            Base64.Decoder decoder = Base64.getDecoder();
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
            System.out.println("클래스명 : "+Thread.currentThread().getStackTrace()[1].getClassName());
            System.out.println("메소드명 : "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new RuntimeException(e);
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
            System.out.println("클래스명 : "+Thread.currentThread().getStackTrace()[1].getClassName());
            System.out.println("메소드명 : "+Thread.currentThread().getStackTrace()[1].getMethodName());
            throw new RuntimeException(ex);
        }
    }
}
