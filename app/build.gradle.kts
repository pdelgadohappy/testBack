plugins {
 	id("com.github.davidmc24.gradle.plugin.avro") version("1.0.0")
	//id("com.gorylenko.gradle-git-properties") version("2.2.4")
	id("org.springframework.boot") version("2.4.5")
	id("io.spring.dependency-management") version("1.0.11.RELEASE")
	java
	jacoco
}

group = "com.happymoney"

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11

avro {
    fieldVisibility.set("PRIVATE")
}


//gitProperties {
//    keys = listOf("git.branch", "git.commit.id", "git.commit.id.abbrev", "git.commit.time", "git.tags", "git.closest.tag.name")
//}

repositories {
    // Use JCenter for resolving dependencies.
    jcenter()
    maven("https://packages.confluent.io/maven/")
}

sourceSets {
	create("intTest") {
		compileClasspath += sourceSets.main.get().output
		runtimeClasspath += sourceSets.main.get().output
	}
}

configurations {
    all {
        exclude(mapOf("group" to "org.springframework.boot", "module" to "spring-boot-starter-logging"))
    }
}

configurations["intTestImplementation"].extendsFrom(configurations.testImplementation.get())
configurations["intTestRuntimeOnly"].extendsFrom(configurations.testRuntimeOnly.get())

dependencies {
	// Platforms
	implementation(platform("org.springframework.boot:spring-boot-dependencies:2.3.4.RELEASE"))

	// Spring dependencies
	implementation("org.springframework.boot:spring-boot-starter")

	// Salesforce SDK
	implementation("com.frejo:force-rest-api:0.0.43")

	// Swagger
	implementation("io.swagger:swagger-jersey2-jaxrs:1.6.2")
	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")

	// REST dependencies
	implementation("org.springframework.boot:spring-boot-starter-jersey")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// Logging dependencies
	implementation("org.springframework.boot:spring-boot-starter-log4j2")

	// Data binding and format dependencies
	implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
	implementation("com.fasterxml.jackson.core:jackson-databind")

	// Kafka dependencies
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.apache.avro:avro:1.10.0")
	implementation("io.confluent:kafka-avro-serializer:5.5.1")

	// Testing dependencies
	testImplementation("org.springframework.kafka:spring-kafka-test")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(mapOf("group" to "org.junit.vintage", "module" to "junit-vintage-engine"))
	}
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
	testImplementation("org.mockito:mockito-core:3.7.7")
	testImplementation("org.mockito:mockito-junit-jupiter:3.7.7")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

	// Use JUnit Jupiter Engine for testing.
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-jersey")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation("io.springfox:springfox-boot-starter:3.0.0")
	implementation("io.springfox:springfox-swagger-ui:3.0.0")
}

tasks.compileJava {
	options.compilerArgs = listOf(
			"-Amapstruct.defaultComponentModel=spring",
			"-Amapstruct.unmappedTargetPolicy=IGNORE"
	)
}

tasks.bootRun {
	environment("spring_profiles_active", "local")
}

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    onlyIf { project.hasProperty("intTest")}

    testClassesDirs = sourceSets["intTest"].output.classesDirs
    classpath = sourceSets["intTest"].runtimeClasspath
    useJUnitPlatform()
    shouldRunAfter("test")
}

tasks.check { dependsOn(integrationTest) }

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    finalizedBy(tasks.jacocoTestCoverageVerification)
    classDirectories.setFrom(
            sourceSets.main.get().output.asFileTree.matching {
                // File Patterns to exclude from testing & code coverage metrics
                exclude()
            }
    )
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            classDirectories.setFrom(sourceSets.main.get().output.asFileTree.matching {
                // File Patterns to exclude from testing & code coverage metrics
                exclude()
            })
            limit {
                // Minimum code coverage % for the build to pass
                minimum = "0.2".toBigDecimal()  //TODO: Raise this value
            }
        }
    }
}
