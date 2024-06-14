package io.github.sebastiankirsch.spring.boot;

import io.github.sebastiankirsch.spring.EnvironmentVariableProtocolResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.lang.NonNull;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * An {@link ApplicationContextInitializer} that adds the {@link EnvironmentVariableProtocolResolver} to the
 * {@link ConfigurableApplicationContext}.<br/>
 * The registered resolver will use the {@link Charset#defaultCharset() default charset} for encoding.
 * To use another charset for encoding,
 * set the application|system property <code>spring.contrib.EnvironmentVariableProtocolResolver.charset</code>.
 *
 * @see EnvironmentVariableProtocolResolver
 * @see ConfigurableApplicationContext#addProtocolResolver(ProtocolResolver)
 */
@SuppressWarnings("unused")
class EnvironmentVariableProtocolResolverApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private EnvironmentVariableProtocolResolverApplicationContextInitializer() {}

	@Override
	public void initialize(@NonNull ConfigurableApplicationContext applicationContext) {
		Log logger = LogFactory.getLog(getClass());
		Optional<Charset> charset = Optional.ofNullable(applicationContext.getEnvironment()
			.getProperty("spring.contrib.EnvironmentVariableProtocolResolver.charset")
		).map(Charset::forName);
		if (logger.isDebugEnabled()) {
			charset.ifPresentOrElse(
				cs -> logger.debug("Registering EnvironmentVariableProtocolResolver with charset [" + cs + "]."),
				() -> logger.debug("Registering EnvironmentVariableProtocolResolver with default charset.")
			);
		}
		EnvironmentVariableProtocolResolver environmentVariableProtocolResolver =
			charset.map(EnvironmentVariableProtocolResolver::new).orElseGet(EnvironmentVariableProtocolResolver::new);
		applicationContext.addProtocolResolver(environmentVariableProtocolResolver);
	}

}