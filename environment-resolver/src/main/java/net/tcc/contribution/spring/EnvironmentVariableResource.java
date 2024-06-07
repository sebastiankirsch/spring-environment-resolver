package net.tcc.contribution.spring;

import org.springframework.core.io.AbstractResource;
import org.springframework.lang.NonNull;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.Charset;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * A {@link org.springframework.core.io.Resource} referring to an environment variable.
 */
class EnvironmentVariableResource extends AbstractResource {

	private final String name;
	private final String value;


	/**
	 * Creates an <code>EnvironmentVariableResource</code>.
	 *
	 * @param name the name of the environment variable to resolve
	 */
	EnvironmentVariableResource(@NonNull String name) {
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

	@Override
	public byte[] getContentAsByteArray() throws FileNotFoundException {
		verifyVariableIsDefined();
		return value.getBytes(UTF_8);
	}

	@Override
	public String getContentAsString(Charset charset) throws FileNotFoundException {
		verifyVariableIsDefined();
		return value;
	}

	@Override
	@NonNull
	public String getDescription() {
		return "ENV:" + name;
	}

	@Override
	@NonNull
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
