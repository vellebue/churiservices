package org.bastanchu.churiservices.orders.internal

import org.bastanchu.churiservices.core.api.config.handler.GlobalExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.net.URI

@RestControllerAdvice
class OrdersExceptionHandler : GlobalExceptionHandler() {
    override fun getTypeURI(): URI {
        return URI.create("/ordersService")
    }
}