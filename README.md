# GraphQL Kotlin Federated Subgraph

[![Deploy on Railway](https://railway.app/button.svg)](https://railway.app/new/template/un6V2G?referralCode=xsbY2R)

This template can be used to create Federated GraphQL subgraph using [GraphQL Kotlin](https://github.com/ExpediaGroup/graphql-kotlin). You can use this template from [Rover](https://www.apollographql.com/docs/rover/commands/template/) with `rover template use --template subgraph-graphql-kotlin`.

GraphQL Kotlin uses reflection API to generate schema directly from your source code. All public functions in classes
implementing `Query`/`Mutation` will be exposed as corresponding queries and mutations.

This example application implements following GraphQL schema:

```graphql
schema
  @contact(description : "send urgent issues to [#oncall](https://yourteam.slack.com/archives/oncall).", name : "FooBar Server Team", url : "https://myteam.slack.com/archives/teams-chat-room-url")
  @link(import : ["key"], url : "https://www.apollographql.com/docs/federation/federation-spec/") {
    query: Query
}

type Foo @key(fields : "id", resolvable : true) {
    id: ID!
    name: String!
}

type Query {
    foo(id: ID!): Foo
}
```

## Build

This project uses [Gradle](https://gradle.com/) and requires Java 17+ runtime. In order to build the project locally (which
will also execute all the tests), simply run the `build` task.

```shell
./gradlew build
```

> NOTE: in order to ensure you use the right version of Gradle we highly recommend to use the provided wrapper script

### Code Quality

Build is configured with [`detekt`](https://detekt.dev/) plugin that runs static code analysis and also [`JaCoCo`](https://www.eclemma.org/jacoco/)
plugin that measures the code coverage. Both plugins are configured to run as part of the build lifecycle and will generate
their reports under `build/reports`. You can invoke both plugins directly using their corresponding tasks.

```shell
./gradlew detekt
./gradlew jacocoTestReport
```

Example integration test is provided. It starts up the SpringBoot server and executes example queries against it.

```shell
./gradlew test
```

### Generating SDL

Build is configured to automatically generate SDL file upon completion. If you need to regenerate the file without running
whole build, you can run the `graphqlGenerateSDL` task directly.

```shell
./gradlew graphqlGenerateSDL
```

### Continuous Integration

This project comes with some example build actions that will trigger on PR requests and commits to the main branch.

## Run

To start the GraphQL server:

* Run `Application.kt` directly from your IDE
* Alternatively you can also run the Spring Boot plugin directly from the command line

```shell script
./gradlew bootRun
```

Once the app has started you can explore the example schema by opening the GraphQL Playground endpoint at http://localhost:8080/playground and begin developing your supergraph with `rover dev --url http://localhost:8080/graphql --name my-sugraph`.

## Apollo Studio Integration

1. Set these secrets in GitHub Actions:
    1. APOLLO_KEY: An Apollo Studio API key for the supergraph to enable schema checks and publishing of the
       subgraph.
    2. APOLLO_GRAPH_REF: The name of the supergraph in Apollo Studio.
    3. PRODUCTION_URL: The URL of the deployed subgraph that the supergraph gateway will route to.
2. Set `SUBGRAPH_NAME` in .github/workflows/checks.yaml and .github/workflows/deploy.yaml
3. Remove the `if: false` lines from `.github/workflows/checks.yaml` and `.github/workflows/deploy.yaml` to enable schema checks and publishing.
4. Write your custom deploy logic in `.github/workflows/deploy.yaml`.
5. Send the `Router-Authorization` header [from your Cloud router](https://www.apollographql.com/docs/graphos/routing/cloud-configuration#managing-secrets) and set the `ROUTER_SECRET` environment variable wherever you deploy this to.

## Additional Resources

* [GraphQL Kotlin documentation](https://opensource.expediagroup.com/graphql-kotlin/)
* [Spring Boot documentation](https://docs.spring.io/spring-boot/docs/2.7.3/reference/htmlsingle/)
* [Gradle documentation](https://docs.gradle.org)


