package org.bastanchu.churiservices.deliveries

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(info = Info(title = "Churiservices Articles API.",
    version = "3.0",
    description = "This is the Articles API to operate into churiservices environment.")
)
class DeliveriesApplication

fun main(args: Array<String>) {
    runApplication<DeliveriesApplication>(*args)
}