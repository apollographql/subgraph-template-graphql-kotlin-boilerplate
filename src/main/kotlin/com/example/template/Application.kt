package com.example.template

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Application {
    // Print out the Apollo Studio Sandbox URL for querying this application
    @Bean
    fun commandLineRunner() = CommandLineRunner {
        println(
            "Apollo Studio Sandbox URL: " +
            "https://studio.apollographql.com/sandbox/explorer?endpoint=http://localhost:8080/graphql"
        )
    }
}

@Suppress("SpreadOperator")
fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
