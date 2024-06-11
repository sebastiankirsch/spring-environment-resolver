# EnvironmentVariableProtocolResolver

A custom Spring [`ProtocolResolver`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/io/ProtocolResolver.html) that resolves environment variables.  
When properly registered, it is used to resolve `ENV:` locations:

	DefaultResourceLoader resourceLoader = ...;
	Resource resource = resourceLoader.getResource("ENV:FOO");
	assertEquals("BAR", resource.getContentAsString(Charset.defaultCharset()));

This allows an application to load resources in different ways,
without the need to add conditional logic à la

	String data = myConfig.loadDataFromEnvironment()
		? System.getenv("FOO")
		: resourceLoader.getResource("classpath:foo.txt").getContentAsString(defaultCharset());

## Usage
### Spring Boot
Just add `environment-resolver-spring-boot-starter` as a dependency.
This will configure the application context (which is injected as `ResourceLoader` during wiring)
to use the `EnvironmentVariableProtocolResolver`.

	<dependency>
		<groupId>io.github.sebastiankirsch.contribution.spring</groupId>
		<artifactId>environment-resolver-spring-boot-starter</artifactId>
		<version>0.1.0</version>
	</dependency>

### Vanilla Spring
You have to manually add the `EnvironmentVariableProtocolResolver` to the `DefaultResourceLoader` you're using.
Note that most application contexts are subclasses of `DefaultResourceLoader`.

	myDefaultResourceLoader.addProtocolResolver(new EnvironmentVariableProtocolResolver());

In this case, you should use the following dependency:

	<dependency>
		<groupId>io.github.sebastiankirsch.contribution.spring</groupId>
		<artifactId>environment-resolver</artifactId>
		<version>0.1.0</version>
	</dependency>
