package com.pallot.cms.conf;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@PropertySource("classpath:/cms-${spring.profiles.active}.properties")
@EnableWebMvc
public class CMS {
	@Configuration
	protected static class ApplicationContextFilterConfiguration {

		@Bean
		public ApplicationContextCMSExceptionFilter cmsApplicationContextIdFilter(ApplicationContext context) {
			return new ApplicationContextCMSExceptionFilter(context);
		}

	}
}
	