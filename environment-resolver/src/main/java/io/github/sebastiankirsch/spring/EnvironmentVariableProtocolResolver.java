package io.github.sebastiankirsch.spring;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.nio.charset.Charset;

/**
 * A {@link ProtocolResolver} resolving a location using the <i><code>ENV:</code> protocol</i> to a {@link Resource}.
 *
 * <h2>Encoding</h2>
 * This resolver returns a <code>Resource</code> based on the <code>String</code> value of {@link System#getenv(String)}.
 * Since such resources offer some methods that require encoding that string (e.g. {@link Resource#getInputStream()}),
 * a {@link Charset} is required.<br/>
 * The default implementation uses the {@link Charset#defaultCharset() default charset} for that.
 * If another charset should be used,
 * one can specify that at {@link #EnvironmentVariableProtocolResolver(Charset) creation time}.
 */
public class EnvironmentVariableProtocolResolver implements ProtocolResolver {

	private static final String ENV_PREFIX = "ENV:";
	@NonNull
	private final Charset charset;

	/**
	 * Creates a new <code>{@link EnvironmentVariableProtocolResolver}</code>,
	 * using the specified charset to encode the resolved strings.
	 *
	 * @param charset	the charset to use for encoding
	 */
	public EnvironmentVariableProtocolResolver(@NonNull Charset charset) {
		this.charset = charset;
	}

	/**
	 * Creates a new <code>{@link EnvironmentVariableProtocolResolver}</code>,
	 * using the {@link Charset#defaultCharset() default charset} to encode the resolved strings.
	 */
	public EnvironmentVariableProtocolResolver() {
		this(Charset.defaultCharset());
	}

	/**
	 * Resolve the given location if it's using the <i><code>ENV:</code> protocol</i>.
	 *
	 * @param 	location the user-specified resource location
	 * @param 	resourceLoader ignored
	 * @return 	an {@link EnvironmentVariableResource} if the location is using the <i><code>ENV:</code> protocol</i>;
	 * 			<code>null</code> otherwise
	 */
	@Override
	@Nullable
	public Resource resolve(@NonNull String location, @Nullable ResourceLoader resourceLoader) {
		return location.startsWith(ENV_PREFIX)
			? new EnvironmentVariableResource(charset, location.substring(ENV_PREFIX.length()))
			: null;
	}

}
