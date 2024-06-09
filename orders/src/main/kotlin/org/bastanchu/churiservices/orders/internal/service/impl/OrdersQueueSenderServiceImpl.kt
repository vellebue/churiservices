package org.bastanchu.churiservices.orders.internal.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.AmqpTemplate

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.service.impl.QueueSenderServiceImpl
import org.bastanchu.churiservices.orders.internal.service.OrdersQueueSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class OrdersQueueSenderServiceImpl(@Autowired private val amqpAdmin : AmqpAdmin,
                                   @Autowired private val amqpTemplate : AmqpTemplate,
                                   @Autowired private val objectMapper : ObjectMapper,
                                   @Autowired private val environment : Environment)
    : QueueSenderServiceImpl<OrderHeader>(amqpAdmin, amqpTemplate, objectMapper, environment)
    , OrdersQueueSenderService  {

    override fun getExchange(): String {
        return environment.getProperty("org.bastanchu.churiservices.rabbitmq.exchanges.orders")!!
    }
}