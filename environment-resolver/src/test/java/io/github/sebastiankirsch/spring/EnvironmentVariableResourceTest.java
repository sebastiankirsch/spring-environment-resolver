package io.github.sebastiankirsch.spring;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

import static com.github.stefanbirkner.systemlambda.SystemLambda.withEnvironmentVariable;
import static java.nio.channels.Channels.newInputStream;
import static java.nio.charset.Charset.defaultCharset;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.util.StreamUtils.copyToString;

class EnvironmentVariableResourceTest {

	@Test
	void assertBehaviorOfUndefinedVariable() throws Exception {
		withEnvironmentVariable("foo", null).execute(() -> {
			EnvironmentVariableResource objectUnderTest = new EnvironmentVariableResource(defaultCharset(), "foo");

			assertThrows(IOException.class, objectUnderTest::contentLength);
			assertFalse(objectUnderTest.exists());
			assertThrows(IOException.class, objectUnderTest::getContentAsByteArray);
			assertThrows(IOException.class, () -> objectUnderTest.getContentAsString(defaultCharset()));
			assertThrows(IOException.class, () -> objectUnderTest.createRelative("foo"));
			assertThrows(IOException.class, objectUnderTest::getFile);
			assertNull(objectUnderTest.getFilename());
			assertThrows(IOException.class, objectUnderTest::getInputStream);
			assertThrows(IOException.class, objectUnderTest::getURI);
			assertThrows(IOException.class, objectUnderTest::getURL);
			assertFalse(objectUnderTest.isFile());
			assertFalse(objectUnderTest.isOpen());
			assertFalse(objectUnderTest.isReadable());
			assertThrows(IOException.class, objectUnderTest::readableChannel);
		});
	}

	@Test
	void assertBehaviorOfDefinedVariable() throws Exception {
		String definedValue = UUID.randomUUID().toString();
		withEnvironmentVariable("foo", definedValue).execute(() -> {
			EnvironmentVariableResource objectUnderTest = new EnvironmentVariableResource(defaultCharset(), "foo");

			assertEquals(definedValue.length(), objectUnderTest.contentLength());
			assertTrue(objectUnderTest.exists());
			assertArrayEquals(definedValue.getBytes(defaultCharset()), objectUnderTest.getContentAsByteArray());
			assertEquals(definedValue, objectUnderTest.getContentAsString(null));
			assertThrows(IOException.class, () -> objectUnderTest.createRelative("foo"));
			assertThrows(IOException.class, objectUnderTest::getFile);
			assertNull(objectUnderTest.getFilename());
			assertEquals(definedValue, copyToString(objectUnderTest.getInputStream(), defaultCharset()));
			assertThrows(IOException.class, objectUnderTest::getURI);
			assertThrows(IOException.class, objectUnderTest::getURL);
			assertFalse(objectUnderTest.isFile());
			assertFalse(objectUnderTest.isOpen());
			assertTrue(objectUnderTest.isReadable());
			assertEquals(definedValue, copyToString(newInputStream(objectUnderTest.readableChannel()), defaultCharset()));
		});
	}

	@Test
	void specifiedEncodingIsUsed() throws Exception {
		String definedValue = "Motörhead";
		withEnvironmentVariable("foo", definedValue).execute(() -> {
			EnvironmentVariableResource objectUnderTest = new EnvironmentVariableResource(ISO_8859_1, "foo");

			assertEquals(definedValue, new String(objectUnderTest.getContentAsByteArray(), ISO_8859_1));
			assertNotEquals(definedValue, new String(objectUnderTest.getContentAsByteArray(), UTF_8));
		});
	}

}