package org.bastanchu.churiservices.articles.internal.handler

import org.bastanchu.churiservices.core.api.config.handler.GlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.net.URI

@RestControllerAdvice
class ArticlesExceptionHandler() : GlobalExceptionHandler() {
    override fun getTypeURI(): URI {
        return URI.create("/articlesService")
    }

}