package org.bastanchu.churiservices.core.api.config.handler

import org.bastanchu.churiservices.core.api.service.exception.ItemNotFoundException
import org.bastanchu.churiservices.core.api.service.exception.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.URI
import java.time.Instant

abstract class GlobalExceptionHandler() : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(e : ServiceException) : ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.message!!)
        problemDetail.title = "Service Exception"
        problemDetail.type = getTypeURI()
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    @ExceptionHandler(ItemNotFoundException::class)
    fun handleItemNotFoundException(e : ItemNotFoundException) : ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.message!!)
        problemDetail.title = "Item Not Found Exception"
        problemDetail.type = getTypeURI()
        problemDetail.setProperty("timestamp", Instant.now())
        return problemDetail
    }

    abstract fun getTypeURI() : URI
}