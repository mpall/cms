package com.pallot.cms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource("classpath:application-test.properties")
@SpringApplicationConfiguration(classes = CMSApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {
	
	@Test
	public void contextLoads() {
	}
}
