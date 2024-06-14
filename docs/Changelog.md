# [in progress](https://github.com/sebastiankirsch/spring-environment-resolver/compare/v0.2.0...main)

## [v0.2.0](https://github.com/sebastiankirsch/spring-environment-resolver/compare/v0.1.0...v0.2.0)
#### `io.github.sebastiankirsch.spring:environment-resolver`
Allow configuration of charset used for encoding via constructor argument of `EnvironmentVariableProtocolResolver`

#### `io.github.sebastiankirsch.spring:environment-resolver-spring-boot-starter`
* Allow configuration of charset used for encoding via application|system property `spring.contrib.EnvironmentVariableProtocolResolver.charset`
* Rename package to `io.github.sebastiankirsch.spring.boot`

#### `io.github.sebastiankirsch.spring:environment-resolver-reactor`
Create aggregate javadoc: https://javadoc.io/doc/io.github.sebastiankirsch.spring/environment-resolver-reactor

## [v0.1.0](https://github.com/sebastiankirsch/spring-environment-resolver/commits/v0.1.0)
First release consisting of two artifacts:

#### `io.github.sebastiankirsch.spring:environment-resolver`
Provides the `EnvironmentVariableProtocolResolver`.

#### `io.github.sebastiankirsch.spring:environment-resolver-spring-boot-starter`  
Automatically registers the `EnvironmentVariableProtocolResolver` at the Spring Boot application context. 