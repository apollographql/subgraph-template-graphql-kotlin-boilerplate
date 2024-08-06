import com.expediagroup.graphql.plugin.gradle.tasks.GraphQLGenerateSDLTask
import com.expediagroup.graphql.plugin.gradle.tasks.GraphQLGenerateTestClientTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.spring") version "2.0.10"

    // graphql plugins
    id("com.expediagroup.graphql") version "7.1.4"

    // code quality plugins
    id("io.gitlab.arturbosch.detekt") version "1.23.6"
    jacoco
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.expediagroup:graphql-kotlin-spring-server:7.1.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("com.expediagroup:graphql-kotlin-spring-client:7.1.4")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.5")

    graphqlSDL("com.expediagroup:graphql-kotlin-federated-hooks-provider:7.1.4")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
    }
    withType<Test> {
        dependsOn("graphqlGenerateTestClient") // test client is required for running tests
        useJUnitPlatform()
    }

    named("build").configure {
        dependsOn("graphqlGenerateSDL") // generate SDL file as part of the build lifecycle
    }
    named("jar") {
        enabled = false
    }

    test {
        finalizedBy("jacocoTestReport") // report is always generated after tests run
    }
    jacocoTestReport {
        dependsOn("test") // tests are required to run before generating the report
    }
}

val graphqlGenerateSDL by tasks.getting(GraphQLGenerateSDLTask::class) {
    packages.set(listOf("com.example.template"))
}
val graphqlGenerateTestClient by tasks.getting(GraphQLGenerateTestClientTask::class) {
    packageName.set("com.example.template.generated")
    schemaFile.set(graphqlGenerateSDL.schemaFile)
}
