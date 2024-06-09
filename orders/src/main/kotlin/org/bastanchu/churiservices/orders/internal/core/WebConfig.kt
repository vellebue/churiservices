package org.bastanchu.churiservices.orders.internal.core

import org.bastanchu.churiservices.core.api.config.BaseWebConfig
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Queue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class WebConfig(@Autowired private val environment : Environment)  : BaseWebConfig() {

    @Bean
    fun ordersToDeliveriesQueue() : Queue {
        return Queue(environment.getProperty("org.bastanchu.churiservices.rabbitmq.queues.orderstodeliveries"), true)
    }

    @Bean
    fun ordersExchange() : DirectExchange {
        return DirectExchange(environment.getProperty("org.bastanchu.churiservices.rabbitmq.exchanges.orders"), true, true)
    }

    @Bean
    fun bindingQueues(@Autowired queue : Queue,
                      @Autowired exchange : DirectExchange) : Binding {
        return BindingBuilder.bind(queue).to(exchange)
            .with(environment.getProperty("org.bastanchu.churiservices.rabbitmq.routingkeys.orderstodeliveries"))
    }
}