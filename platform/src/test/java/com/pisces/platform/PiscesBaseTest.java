package com.pisces.platform;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.pisces.core.utils.AppUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("dev")
@Transactional
public class PiscesBaseTest {
	@Autowired
	private ApplicationContext ctx;
	
	@Autowired
    protected TestRestTemplate restTemplate;
	
	@Before
	public void login() {
		AppUtils.setContext(ctx);
		AppUtils.login("niuhaitao");
	}
}
