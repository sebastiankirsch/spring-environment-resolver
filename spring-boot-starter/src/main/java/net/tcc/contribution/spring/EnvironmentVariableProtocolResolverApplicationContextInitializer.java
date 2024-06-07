package net.tcc.contribution.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ProtocolResolver;

/**
 * An {@link ApplicationContextInitializer} that adds the {@link EnvironmentVariableProtocolResolver} to the
 * {@link ConfigurableApplicationContext}.
 *
 * @see ConfigurableApplicationContext#addProtocolResolver(ProtocolResolver)
 */
class EnvironmentVariableProtocolResolverApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		applicationContext.addProtocolResolver(new EnvironmentVariableProtocolResolver());
	}

}