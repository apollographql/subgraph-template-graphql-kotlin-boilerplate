package com.example.template

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

/**
 * If the ROUTER_SECRET environment variable is set, then this filter will require that all requests have
 * a `Router-Authorization` header matching that value.
 *
 */
@Component
class RouterAuthFilter : WebFilter {
    override fun filter(ctx: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val secret = System.getenv("ROUTER_SECRET")
        return if (secret != null) {
            val authHeader = ctx.request.headers.getFirst("Router-Authorization")
            if (authHeader == secret) {
                chain.filter(ctx)
            } else {
                ctx.response.statusCode = HttpStatus.UNAUTHORIZED
                Mono.empty()
            }
        } else {
            chain.filter(ctx)
        }
    }

}
