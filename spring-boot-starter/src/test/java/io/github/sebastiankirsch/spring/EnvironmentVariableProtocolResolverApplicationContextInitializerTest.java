package io.github.sebastiankirsch.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.UUID;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvironmentVariableProtocolResolverApplicationContextInitializerTest {

	@Test
	void environmentVariableProtocolResolverIsRegisteredAtApplicationContext() throws Exception {
		String definedValue = UUID.randomUUID().toString();
		withEnvironmentVariable("foo", definedValue).execute(() -> {
			SpringApplication springApplication = new SpringApplication(SpringConfiguration.class);
			springApplication.setBannerMode(Banner.Mode.OFF);

			try (ConfigurableApplicationContext context = springApplication.run()) {
				Resource value = context.getBean("foo", Resource.class);
				assertEquals(definedValue, value.getContentAsString(defaultCharset()));
			}
		});
	}

	@Configuration
	static class SpringConfiguration {
		@Bean
		Resource foo(ResourceLoader resourceLoader) {
			return resourceLoader.getResource("ENV:foo");
		}
	}

}