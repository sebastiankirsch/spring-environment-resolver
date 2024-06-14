# EnvironmentVariableProtocolResolver
[![Maven Central Version](https://img.shields.io/maven-central/v/io.github.sebastiankirsch.spring/environment-resolver-reactor)](https://central.sonatype.com/artifact/io.github.sebastiankirsch.spring/environment-resolver-reactor)
[![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/sebastiankirsch/spring-environment-resolver/maven-verify.yml)](https://github.com/sebastiankirsch/spring-environment-resolver/actions/workflows/maven-verify.yml?query=branch%3Amain++)
[![SonarCloud QG](https://sonarcloud.io/api/project_badges/measure?project=sebastiankirsch_spring-environment-resolver&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=sebastiankirsch_spring-environment-resolver)
[![javadoc.io version](https://javadoc.io/badge2/io.github.sebastiankirsch.spring/environment-resolver-reactor/javadoc.svg)](https://javadoc.io/doc/io.github.sebastiankirsch.spring/environment-resolver-reactor)

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
		<groupId>io.github.sebastiankirsch.spring</groupId>
		<artifactId>environment-resolver-spring-boot-starter</artifactId>
		<version>0.2.0</version>
	</dependency>

### Vanilla Spring
You have to manually add the `EnvironmentVariableProtocolResolver` to the `DefaultResourceLoader` you're using.
Note that most application contexts are subclasses of `DefaultResourceLoader`.

	myDefaultResourceLoader.addProtocolResolver(new EnvironmentVariableProtocolResolver());

In this case, you should use the following dependency:

	<dependency>
		<groupId>io.github.sebastiankirsch.spring</groupId>
		<artifactId>environment-resolver</artifactId>
		<version>0.2.0</version>
	</dependency>

## Encoding

The `EnvironmentVariableProtocolResolver` returns a `Resource` based on `System.getenv`.
Since such resources offer some methods that require encoding (e.g. `Resource.getInputStream`), a `Charset` is required.

### Spring Boot
The registered resolver will use the default charset for encoding.
To use another charset for encoding,
set the application|system property `spring.contrib.EnvironmentVariableProtocolResolver.charset`.

### Vanilla Spring
The default implementation uses the default charset for that.
If another charset should be used, one can specify that by using the appropriate constructor.