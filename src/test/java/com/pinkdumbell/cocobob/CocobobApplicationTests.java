package com.pinkdumbell.cocobob;

import com.pinkdumbell.cocobob.common.EmailUtil;
import com.pinkdumbell.cocobob.config.MailConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CocobobApplicationTests {

	@MockBean
	EmailUtil emailUtil;

	@MockBean
	MailConfig mailConfig;
	@Test
	void contextLoads() {
	}

}
