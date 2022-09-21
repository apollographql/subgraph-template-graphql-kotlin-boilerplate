package com.example.template

import com.example.template.generated.FooQuery
import com.expediagroup.graphql.client.spring.GraphQLWebClient
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.reactive.server.WebTestClient
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTest(@Autowired private val webTestClient: WebTestClient, @LocalServerPort private val port: Int) {

    // tests are equivalent
	@Test
	fun `verifies foo query response using type safe client`() = runTest {
        val client = GraphQLWebClient(url = "http://localhost:$port/graphql")

        val fooQueryResponse = client.execute(FooQuery())
        assertNull(fooQueryResponse.errors)

        val fooResponse = fooQueryResponse.data?.foo
        assertNotNull(fooResponse)
        assertEquals("1", fooResponse.id)
        assertEquals("Name", fooResponse.name)
	}

    @Test
    fun `verifies foo query response using Spring WebTestClient and JSONPath`() {
        webTestClient.post()
            .uri("/graphql")
            .accept(APPLICATION_JSON)
            .contentType(APPLICATION_JSON)
            .bodyValue("""{ "query": "query FooQuery { foo(id: \"1\") { id name } }" }""")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.errors").doesNotExist()
            .jsonPath("$.data").exists()
            .jsonPath("$.data.foo").exists()
            .jsonPath("$.data.foo.id").isEqualTo("1")
            .jsonPath("$.data.foo.name").isEqualTo("Name")
    }
}
