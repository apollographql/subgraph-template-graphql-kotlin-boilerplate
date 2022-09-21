package com.example.template.model

import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import com.expediagroup.graphql.generator.federation.execution.FederatedTypeResolver
import com.expediagroup.graphql.generator.scalars.ID
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@KeyDirective(fields = FieldSet("id"))
data class Foo(val id: ID, val name: String)

@Component
class FooResolver : FederatedTypeResolver<Foo> {
    override val typeName: String
        get() = "Foo"

    override suspend fun resolve(
        environment: DataFetchingEnvironment,
        representations: List<Map<String, Any>>
    ): List<Foo?> {
        return representations.map {
            val id = it["id"]
            if (id == "1") {
                Foo(ID("1"), "Name")
            } else {
                null
            }
        }
    }
}
