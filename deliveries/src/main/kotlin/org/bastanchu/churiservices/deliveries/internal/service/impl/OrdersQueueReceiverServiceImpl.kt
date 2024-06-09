package org.bastanchu.churiservices.deliveries.internal.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.service.QueueReceiverService
import org.bastanchu.churiservices.core.api.service.impl.QueueReceiverServiceImpl
import org.bastanchu.churiservices.deliveries.internal.service.OrdersQueueReceiverService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service

@Service
class OrdersQueueReceiverServiceImpl(@Autowired private val objectMapper : ObjectMapper,
                                     @Autowired private val environment: Environment)
    : QueueReceiverServiceImpl<OrderHeader>(objectMapper, environment)
    , OrdersQueueReceiverService
    {
        val logger = LoggerFactory.getLogger(OrdersQueueReceiverServiceImpl::class.java)

        @RabbitListener(queues = ["org.bastanchu.churiservices.orders.orderstodeliveriesQueue"])
        override fun proccessMessage(content: String) {
            super.proccessMessage(content)
        }

        override fun onReceiveMessage(value: OrderHeader) {
            logger.info("Received order with order customer id ${value.customerOrderId}")
        }
    }