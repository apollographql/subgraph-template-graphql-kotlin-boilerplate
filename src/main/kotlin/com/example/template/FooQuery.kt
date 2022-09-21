package com.example.template

import com.example.template.model.Foo
import com.expediagroup.graphql.generator.scalars.ID
import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class FooQuery : Query {

    fun foo(id: ID): Foo? = if (id.value == "1") {
        Foo(ID("1"), "Name")
    } else {
        null
    }
}
