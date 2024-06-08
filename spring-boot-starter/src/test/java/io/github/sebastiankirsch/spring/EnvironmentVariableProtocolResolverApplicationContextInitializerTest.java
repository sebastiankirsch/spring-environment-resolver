package io.github.sebastiankirsch.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;

class EnvironmentVariableProtocolResolverApplicationContextInitializerTest {

	@Test
	void testApplicationStartUp() throws Exception {
		String setValue = UUID.randomUUID().toString();
		withEnvironmentVariable("foo", setValue).and("bar", null).execute(() -> {
			SpringApplication springApplication = new SpringApplication(SpringConfiguration.class);
			springApplication.setBannerMode(Banner.Mode.OFF);
			ConfigurableApplicationContext context = springApplication.run();

			Resource value = context.getBean("foo", Resource.class);

			Assertions.assertEquals(setValue, value.getContentAsString(StandardCharsets.UTF_8));
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