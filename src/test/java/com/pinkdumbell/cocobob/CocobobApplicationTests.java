package com.pinkdumbell.cocobob;

import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.config.MailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CocobobApplicationTests {

	@MockBean
	EmailUtil emailUtil;
	@MockBean
	MailConfig mailConfig;
	@Value("${google.auth.url}")
	private String googleAuthUrl;
	@Value("${google.login.url}")
	private String googleLoginUrl;
	@Value("${google.client.id}")
	private String googleClientId;
	@Value("${google.client.secret}")
	private String googleClientSecret;
	@Value("${google.redirect.url}")
	private String googleRedirectUrl;

	@Test
	void contextLoads() {
	}

}
