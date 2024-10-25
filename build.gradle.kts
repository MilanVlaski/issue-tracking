plugins {
	java
	id("org.springframework.boot") version "3.3.4"
	id("io.spring.dependency-management") version "1.1.6"
}

group = "com.akimi"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.cucumber:cucumber-java:7.20.1")
	testImplementation("io.cucumber:cucumber-junit:7.20.1")
	testImplementation("io.cucumber:cucumber-junit-platform-engine:7.20.1")
	testImplementation("io.cucumber:cucumber-spring:7.20.1")
	testImplementation("org.junit.jupiter:junit-jupiter:5.11.2")
	testImplementation("org.junit.platform:junit-platform-suite:1.11.2")
	testImplementation("org.seleniumhq.selenium:selenium-java:4.25.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
