package io.github.sebastiankirsch.spring;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class EnvironmentVariableProtocolResolverTest {

	private final EnvironmentVariableProtocolResolver objectUnderTest = new EnvironmentVariableProtocolResolver();

	@Test
	void resolvesEnvProtocol() {
		assertNotNull(objectUnderTest.resolve("ENV:foo", null));
	}

	@Test
	void ignoresNonEnvProtocol() {
		assertNull(objectUnderTest.resolve("ENV|foo", null));
	}

}