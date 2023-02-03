package com.example.template

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class CorsFilter : WebFilter {
    override fun filter(ctx: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        ctx.response.headers.add("Access-Control-Allow-Origin", "https://studio.apollographql.com")
        ctx.response.headers.add("Access-Control-Allow-Methods", "POST")
        ctx.response.headers.add("Access-Control-Allow-Headers", "*")
        ctx.response.headers.add("Access-Control-Allow-Credentials", "true")
        return when (ctx.request.method) {
            HttpMethod.OPTIONS -> {
                ctx.response.headers.add("Access-Control-Max-Age", "1728000")
                ctx.response.statusCode = HttpStatus.NO_CONTENT
                Mono.empty()
            }
            else -> {
                chain.filter(ctx)
            }
        }
    }

}
