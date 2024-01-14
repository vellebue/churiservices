package org.bastanchu.churiservices.orders.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Encoding
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.bastanchu.churiservices.core.api.model.Envelope
import org.bastanchu.churiservices.core.api.model.GenericResponse
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.orders.internal.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.data.domain.Example
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

@RestController
class OrderController(@Autowired val orderService : OrderService,
                      @Autowired val environment: Environment) {

    @Operation(
        summary = "Creates an orden given its header deatils, delivery address, invoice address and order line items required.",
        requestBody = RequestBody(
            required = true, content = [
                Content(
                    mediaType = "application/json",
                    examples = [ExampleObject(
                        """
                            {
                        "transactionId": "TidOcjhV4Ue4h7TysNHtqrI/lgkZcySkOKgtJw6yvAc=",
                        "correlationId": "TidOcjhV4Ue4h7TysNHtqrI/lgkZcySkOKgtJw6yvAc=",
                        "timestamp": "2024-01-07 13:52:20 +0100",
                        "data": {
                            "customerOrderId": "ACV-7365513",
                            "customerId": "00018122232",
                            "deliveryAdress": {
                                "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                "zipCode": "28922",
                                "city": "Alcorcón",
                                "regionId": "28",
                                "regionName": "Madrid",
                                "countryId": "ES",
                                "countryName": "Spain"
                            },
                            "invoiceAddress": {
                                "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                "zipCode": "28922",
                                "city": "Alcorcón",
                                "regionId": "28",
                                "regionName": "Madrid",
                                "countryId": "ES",
                                "countryName": "Spain"
                            },
                            "baseValue": 371.26,
                            "vatTaxValue": 152.34,
                            "totalValue": 523.60,
                            "lines": [
                                {
                                    "articleId": "ORTE37553",
                                    "articleDescription": "Compaq Laptop Multimedia Series 17\".",
                                    "numItems": 3,
                                    "basePrice": 1200.700,
                                    "vatTax": 120.070,
                                    "totalPrice": 1320.770,
                                    "totalValue": 3960.220
                                }
                            ]
                        }
                    }   
                        """
                    )]
                )

            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201", description = "Order sucessfully created.", content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                """
                    {
                        "transactionId": "TidOcjhV4Ue4h7TysNHtqrI/lgkZcySkOKgtJw6yvAc=",
                        "correlationId": "TidOcjhV4Ue4h7TysNHtqrI/lgkZcySkOKgtJw6yvAc=",
                        "timestamp": "2024-01-07 13:52:20 +0100",
                        "data": {
                            "orderId": 1,
                            "customerOrderId": "ACV-7365513",
                            "customerId": "00018122232",
                            "deliveryAdress": {
                                "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                "zipCode": "28922",
                                "city": "Alcorcón",
                                "regionId": "28",
                                "regionName": "Madrid",
                                "countryId": "ES",
                                "countryName": "Spain"
                            },
                            "invoiceAddress": {
                                "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                "zipCode": "28922",
                                "city": "Alcorcón",
                                "regionId": "28",
                                "regionName": "Madrid",
                                "countryId": "ES",
                                "countryName": "Spain"
                            },
                            "baseValue": 371.26,
                            "vatTaxValue": 152.34,
                            "totalValue": 523.60,
                            "lines": [
                                {
                                    "articleId": "ORTE37553",
                                    "articleDescription": "Compaq Laptop Multimedia Series 17\".",
                                    "numItems": 3,
                                    "basePrice": 1200.700,
                                    "vatTax": 120.070,
                                    "totalPrice": 1320.770,
                                    "totalValue": 3960.220
                                }
                            ]
                        }
                    }
                """
                            )]
                    )]
            )
        ]
    )
    @PostMapping("/orders/order")
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@org.springframework.web.bind.annotation.RequestBody order: Envelope<OrderHeader>): Envelope<OrderHeader> {
        val timestampDateFormat =
            SimpleDateFormat(environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
        val orderHeader = order.data!!
        orderService.createOrder(orderHeader)
        val outEnvelope = Envelope<OrderHeader>()
        outEnvelope.data = orderHeader
        outEnvelope.correlationId = order.correlationId
        outEnvelope.transactionId = order.transactionId
        outEnvelope.timestamp = timestampDateFormat.format(Date())
        return outEnvelope
    }

    @Operation(summary = "Retrieves an order details data structure given an order id \"orderId\".")
    @ApiResponses(value = arrayOf(
        ApiResponse(responseCode = "200", description = "Order successfully retrieved as described.", content = [
            Content(mediaType = "application/json",
                    examples = [
                        ExampleObject("""
                            {
                                "transactionId": "XL9xEdxFOomy31RgjmwLJzolbgPSvflKqByqFvtA6Ro=",
                                "correlationId": "XL9xEdxFOomy31RgjmwLJzolbgPSvflKqByqFvtA6Ro=",
                                "timestamp": "2024-01-14 19:43:18 +0100",
                                "data": {
                                    "orderId": 4,
                                    "customerOrderId": "NIA-3127172",
                                    "customerId": "00018122232",
                                    "deliveryAdress": {
                                        "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                        "zipCode": "28922",
                                        "city": "Alcorcón",
                                        "regionId": "28",
                                        "regionName": "Madrid",
                                        "countryId": "ES",
                                        "countryName": "Spain"
                                    },
                                    "invoiceAddress": {
                                        "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                        "zipCode": "28922",
                                        "city": "Alcorcón",
                                        "regionId": "28",
                                        "regionName": "Madrid",
                                        "countryId": "ES",
                                        "countryName": "Spain"
                                    },
                                    "baseValue": 9600.01,
                                    "vatTaxValue": 960.21,
                                    "totalValue": 10560.22,
                                    "lines": [
                                        {
                                            "articleId": "ORTE37553",
                                            "articleDescription": "Compaq Laptop Multimedia Series 17\".",
                                            "numItems": 3,
                                            "basePrice": 1200.700,
                                            "vatTax": 120.070,
                                            "totalPrice": 1320.770,
                                            "totalValue": 3960.220
                                        },
                                        {
                                            "articleId": "PEKI37553",
                                            "articleDescription": "NVIDIA GeForce 3070 RTX",
                                            "numItems": 4,
                                            "basePrice": 1500.000,
                                            "vatTax": 150.000,
                                            "totalPrice": 1650.000,
                                            "totalValue": 6600.000
                                        }
                                    ]
                                }
                            }
                        """)
                    ])
        ]),
        ApiResponse(responseCode = "404", description = "There is no order registered under given orderId.",
                    content = [
                        Content(mediaType = "application/json",
                                examples = [
                                    ExampleObject("""
                                        {
                                            "transactionId": "Pl+h5YHzMfmRdz14N6GB2w7FnAR/fbk15d/Oa+n7Wco=",
                                            "correlationId": "Pl+h5YHzMfmRdz14N6GB2w7FnAR/fbk15d/Oa+n7Wco=",
                                            "timestamp": "2024-01-14 20:02:02 +0100",
                                            "data": {
                                                "status": "FAILURE",
                                                "description": "Order 22 not found.",
                                                "timestamp": "2024-01-14 20:02:02 +0100"
                                            }
                                        }
                                    """)
                                ])
                    ])
    ))
    @GetMapping("/orders/order/{orderId}")
    fun getOrder(@PathVariable orderId : Int) :ResponseEntity<Envelope<out Any>> {
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
            val genericEnvelope = Envelope<GenericResponse>()
            genericEnvelope.correlationId = Base64.getEncoder().encodeToString(hashMark)
            genericEnvelope.transactionId = Base64.getEncoder().encodeToString(hashMark)
            genericEnvelope.timestamp = timestampDateFormat.format(Date())
            genericEnvelope.data = GenericResponse(status = GenericResponse.Status.FAILURE,
                                                   description = "Order " + orderId +  " not found.",
                                                   timestamp = timestampDateFormat.format(Date()))
            return ResponseEntity(genericEnvelope, HttpStatus.NOT_FOUND)
        }
     }
}