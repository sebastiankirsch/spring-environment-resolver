package io.github.sebastiankirsch.spring;

import org.springframework.core.io.AbstractResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * A {@link org.springframework.core.io.Resource} referring to an environment variable.
 */
class EnvironmentVariableResource extends AbstractResource {

	private final Charset charset;
	private final String name;
	private final String value;

	/**
	 * Creates an <code>EnvironmentVariableResource</code>.
	 *
	 * @param charset the <code>Charset</code> to use when encoding the referred value to bytes
	 *                or providing an input stream
	 * @param name    the name of the environment variable to resolve
	 */
	EnvironmentVariableResource(Charset charset, @NonNull String name) {
		this.charset = charset;
		this.name = name;
		this.value = System.getenv(name);
	}

	@Override
	public long contentLength() throws IOException {
		return getContentAsByteArray().length;
	}

	@Override
	public boolean exists() {
		return value != null;
	}

	@NonNull
	@Override
	public byte[] getContentAsByteArray() throws FileNotFoundException {
		verifyVariableIsDefined();
		return value.getBytes(charset);
	}

	@NonNull
	@Override
	public String getContentAsString(@Nullable Charset charset) throws FileNotFoundException {
		verifyVariableIsDefined();
		return value;
	}

	@NonNull
	@Override
	public String getDescription() {
		return "ENV:" + name;
	}

	@NonNull
	@Override
	public InputStream getInputStream() throws FileNotFoundException {
		return new ByteArrayInputStream(getContentAsByteArray());
	}

	@Override
	public long lastModified() throws FileNotFoundException {
		return Optional.ofNullable(ManagementFactory.getRuntimeMXBean()).map(RuntimeMXBean::getStartTime)
			.orElseThrow(() -> new FileNotFoundException("JVM uptime cannot be determined!"));
	}

	private void verifyVariableIsDefined() throws FileNotFoundException {
		if (!exists()) {
			throw new FileNotFoundException("Environment variable [" + name + "] is not defined!");
		}
	}

}
