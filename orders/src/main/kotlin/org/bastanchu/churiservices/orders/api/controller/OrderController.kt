package org.bastanchu.churiservices.orders.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.service.exception.ItemNotFoundException
import org.bastanchu.churiservices.orders.internal.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

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
                                "customerOrderId": "NIA-3127172",
                                "customerId": "00018122232",
                                "deliveryAdress": {
                                  "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                  "zipCode": "28922",
                                  "city": "Alcorcón",
                                  "regionId": "28",
                                  "regionName": "MADRID",
                                  "countryId": "ES",
                                  "countryName": "Spain"
                                },
                                "invoiceAddress": {
                                  "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                  "zipCode": "28922",
                                  "city": "Alcorcón",
                                  "regionId": "28",
                                  "regionName": "MADRID",
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
                                    "basePrice": 1200.7,
                                    "vatTax": 120.07,
                                    "totalPrice": 1320.77,
                                    "totalValue": 3960.22
                                  },
                                  {
                                    "articleId": "PEKI37553",
                                    "articleDescription": "NVIDIA GeForce 3070 RTX",
                                    "numItems": 4,
                                    "basePrice": 1500.0,
                                    "vatTax": 150,
                                    "totalPrice": 1650,
                                    "totalValue": 6600
                                  }
                                ]
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
                                        "orderId": 3,
                                        "customerOrderId": "NIA-3127172",
                                        "customerId": "00018122232",
                                        "deliveryAdress": {
                                            "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                            "zipCode": "28922",
                                            "city": "Alcorcón",
                                            "regionId": "28",
                                            "regionName": "MADRID",
                                            "countryId": "ES",
                                            "countryName": "Spain"
                                        },
                                        "invoiceAddress": {
                                            "address": "Calle Mayor 21 Esc. Izq. 9º B",
                                            "zipCode": "28922",
                                            "city": "Alcorcón",
                                            "regionId": "28",
                                            "regionName": "MADRID",
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
                                                "basePrice": 1200.7,
                                                "vatTax": 120.07,
                                                "totalPrice": 1320.77,
                                                "totalValue": 3960.22
                                            },
                                            {
                                                "articleId": "PEKI37553",
                                                "articleDescription": "NVIDIA GeForce 3070 RTX",
                                                "numItems": 4,
                                                "basePrice": 1500.0,
                                                "vatTax": 150,
                                                "totalPrice": 1650,
                                                "totalValue": 6600
                                            }
                                        ]
                                    }
                            """)]
                    )]
            )
        ]
    )
    @PostMapping("/orders/order")
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@org.springframework.web.bind.annotation.RequestBody orderHeader: OrderHeader): OrderHeader {
        orderService.createOrder(orderHeader)
        return orderHeader
    }

    @Operation(summary = "Retrieves an order details data structure given an order id \"orderId\".")
    @ApiResponses(value = arrayOf(
        ApiResponse(responseCode = "200", description = "Order successfully retrieved as described.", content = [
            Content(mediaType = "application/json",
                    examples = [
                        ExampleObject("""
                           {
                            "orderId": 1,
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
                        """)
                    ])
        ]),
        ApiResponse(responseCode = "404", description = "There is no order registered under given orderId.",
                    content = [
                        Content(mediaType = "application/json",
                                examples = [
                                    ExampleObject("""
                                       {
                                            "type": "/ordersService",
                                            "title": "Item Not Found Exception",
                                            "status": 404,
                                            "detail": "Order with order id 6 not found",
                                            "instance": "/orders/order/6",
                                            "timestamp": "2024-03-31T18:36:25.002729660Z"
                                        }
                                    """)
                                ])
                    ])
    ))
    @GetMapping("/orders/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    fun getOrder(@PathVariable orderId : Int) : OrderHeader {
        val orderHeader = orderService.getOrder(orderId)
        if (orderHeader != null) {
            return orderHeader
        } else {
            throw ItemNotFoundException("Order with order id ${orderId} not found")
        }
     }
}