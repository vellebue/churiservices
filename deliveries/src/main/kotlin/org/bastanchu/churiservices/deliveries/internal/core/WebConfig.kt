package org.bastanchu.churiservices.deliveries.internal.core

import org.bastanchu.churiservices.core.api.config.BaseWebConfig
import org.springframework.amqp.core.Queue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class WebConfig(@Autowired val environment : Environment) : BaseWebConfig() {

    @Bean
    fun ordersToDeliveriesQueue() : Queue {
        return Queue(environment.getProperty("org.bastanchu.churiservices.rabbitmq.queues.orderstodeliveries"), true)
    }

}