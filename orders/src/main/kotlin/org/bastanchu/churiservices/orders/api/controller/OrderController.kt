package org.bastanchu.churiservices.orders.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.bastanchu.churiservices.core.api.model.Envelope
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.orders.internal.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

@RestController
class OrderController(@Autowired val orderService : OrderService,
                      @Autowired val environment: Environment) {

    @Operation(summary = "Creates an orden given its header deatils, delivery address, invoice address and order line items required.")
    @ApiResponses(value = arrayOf(
        ApiResponse(responseCode = "201", description = "Order sucessfully created.")
    ))
    @PostMapping("/orders/order")
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody order : Envelope<OrderHeader>) : Envelope<OrderHeader> {
        val timestampDateFormat = SimpleDateFormat(environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
        val orderHeader = order.data!!
        orderService.createOrder(orderHeader)
        val outEnvelope = Envelope(data = orderHeader)
        outEnvelope.correlationId = order.correlationId
        outEnvelope.transactionId = order.transactionId
        outEnvelope.timestamp = timestampDateFormat.format(Date())
        return outEnvelope
    }

    @Operation(summary = "Retrieves an order details data structure given an order id \"orderId\".")
    @ApiResponses(value = arrayOf(
        ApiResponse(responseCode = "200", description = "Order successfully retrieved as described."),
        ApiResponse(responseCode = "404", description = "There is no order registered under given orderId.", )
    ))
    @GetMapping("/orders/order/{orderId}")
    fun getOrder(@PathVariable orderId : Int) :ResponseEntity<Envelope<OrderHeader>> {
        val timestampDateFormat = SimpleDateFormat(environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
        val orderHeader = orderService.getOrder(orderId)
        val outEnvelope = Envelope(data = orderHeader)
        val digest = MessageDigest.getInstance("SHA-256");
        val hashMark = digest.digest(Date().toString().encodeToByteArray())
        outEnvelope.correlationId = Base64.getEncoder().encodeToString(hashMark)
        outEnvelope.transactionId = Base64.getEncoder().encodeToString(hashMark)
        outEnvelope.timestamp = timestampDateFormat.format(Date())
        if (orderHeader != null) {
            return ResponseEntity(outEnvelope, HttpStatus.OK)
        } else {
            outEnvelope.data = OrderHeader()
            return ResponseEntity(outEnvelope, HttpStatus.NOT_FOUND)
        }
     }
}