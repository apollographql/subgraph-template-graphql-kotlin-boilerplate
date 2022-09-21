package com.example.template

import com.expediagroup.graphql.generator.federation.directives.ContactDirective
import com.expediagroup.graphql.server.Schema
import org.springframework.stereotype.Component

@ContactDirective(
    name = "FooBar Server Team",
    url = "https://myteam.slack.com/archives/teams-chat-room-url",
    description = "send urgent issues to [#oncall](https://yourteam.slack.com/archives/oncall)."
)
@Component
class SchemaContact : Schema
