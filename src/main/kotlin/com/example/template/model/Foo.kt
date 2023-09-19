package com.example.template.model

import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import com.expediagroup.graphql.generator.federation.execution.FederatedTypeSuspendResolver
import com.expediagroup.graphql.generator.scalars.ID
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@KeyDirective(fields = FieldSet("id"))
data class Foo(val id: ID, val name: String)

@Component
class FooResolver : FederatedTypeSuspendResolver<Foo> {
    override val typeName: String
        get() = "Foo"

    override suspend fun resolve(
        environment: DataFetchingEnvironment,
        representation: Map<String, Any>
    ): Foo? {
        val id = representation["id"]
        return if (id == "1") {
            Foo(ID("1"), "Name")
        } else {
            null
        }
    }
}
