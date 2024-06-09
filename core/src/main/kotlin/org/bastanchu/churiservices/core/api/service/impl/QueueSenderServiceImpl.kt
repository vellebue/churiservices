package org.bastanchu.churiservices.core.api.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.bastanchu.churiservices.core.api.config.component.Slf4jInterceptor
import org.bastanchu.churiservices.core.api.model.Envelope
import org.bastanchu.churiservices.core.api.service.QueueSenderService
import org.slf4j.LoggerFactory
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.AmqpTemplate
import org.springframework.amqp.core.Message
import org.springframework.core.env.Environment
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import java.lang.reflect.ParameterizedType
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

abstract class QueueSenderServiceImpl<T>(private val amqpAdmin :AmqpAdmin,
                                         private val amqpTemplate : AmqpTemplate,
                                         private val objectMapper : ObjectMapper,
                                         private val environment : Environment)
    : QueueSenderService<T> {

    private val logger = LoggerFactory.getLogger(QueueSenderService::class.java)
    private val localTimestampFormat : DateFormat =
        SimpleDateFormat(environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
    private val valueClass : Class<T> = getParameterizedType(0)


    abstract fun getExchange() : String

    override fun send(routingKey : String, value : T) {
        logger.info("Sending to queue exchange ${getExchange()}")
        val requestAttributes = RequestContextHolder.getRequestAttributes()!!
        val correlationId = requestAttributes
            .getAttribute(Slf4jInterceptor.correlationIdLogAttribute, RequestAttributes.SCOPE_REQUEST) as String? ?: ""
        val transactionId = requestAttributes
            .getAttribute(Slf4jInterceptor.transactionIdLogAttribute, RequestAttributes.SCOPE_REQUEST) as String? ?: ""
        val envelope = Envelope<T>()
        envelope.correlationId = correlationId
        envelope.transactionId = transactionId
        envelope.timestamp = localTimestampFormat.format(Date())
        envelope.data = value
        val bytes = objectMapper.writeValueAsBytes(envelope)
        amqpTemplate.send(getExchange(),routingKey, Message(bytes))
    }

    override fun send(value: T) {
        send("", value)
    }

    fun <K> getParameterizedType(order : Int) : Class<K> {
        val genericSuperclass = this::class.java.genericSuperclass;
        if (genericSuperclass is ParameterizedType) {
            val parameterizedType : ParameterizedType = genericSuperclass
            return parameterizedType.actualTypeArguments[order] as Class<K>
        } else {
            throw ClassNotFoundException("No parameterized class defined (this may not happen)")
        }
    }
}