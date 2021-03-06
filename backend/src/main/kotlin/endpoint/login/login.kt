package org.ninrod.blog.endpoint.login

import io.ktor.application.call
import io.ktor.auth.UserPasswordCredential
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.post
import org.ninrod.blog.infra.JwtConfig
import org.ninrod.blog.infra.Token
import org.ninrod.blog.user.findUserByCredentials

fun Route.login() {
    post("/login") {
        // get client ip behind proxy
        // https://github.com/ktorio/ktor/issues/351
        findUserByCredentials(call.receive<UserPasswordCredential>())?.let {
            u -> call.respond(Token(JwtConfig.makeToken(u)))
        } ?: call.respond(HttpStatusCode.Unauthorized)
    }
}