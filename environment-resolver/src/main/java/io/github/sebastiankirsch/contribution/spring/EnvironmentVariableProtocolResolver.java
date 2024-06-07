package io.github.sebastiankirsch.contribution.spring;

import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * A {@link ProtocolResolver} resolving a location using the <i><code>ENV:</code> protocol</i> to a {@link Resource}.
 */
public class EnvironmentVariableProtocolResolver implements ProtocolResolver {

	private static final String ENV_PREFIX = "ENV:";

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
			? new EnvironmentVariableResource(location.substring(ENV_PREFIX.length()))
			: null;
	}

}
