package org.bastanchu.churiservices.core.api.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.type.TypeFactory
import org.bastanchu.churiservices.core.api.config.component.Slf4jInterceptor
import org.bastanchu.churiservices.core.api.model.Envelope
import org.bastanchu.churiservices.core.api.service.QueueReceiverService
import org.slf4j.MDC
import org.springframework.core.env.Environment
import java.lang.reflect.ParameterizedType
import java.util.UUID

abstract class QueueReceiverServiceImpl<T>(private val objectMapper : ObjectMapper,
                                           private val environment : Environment)
    : QueueReceiverService<T> {

    private val valueClass : Class<T> = getParameterizedType<T>(0)

    override fun proccessMessage(content: String) {
        val envelopeJavaType = TypeFactory.defaultInstance().constructParametricType(Envelope::class.java, valueClass)
        val envelope = objectMapper.readValue<Envelope<T>>(content.toByteArray(), envelopeJavaType)
        enableCorrelationId(envelope.correlationId)
        val dataValue = envelope.data!!
        onReceiveMessage(dataValue)
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

    private fun enableCorrelationId(correlationId: String?) {
        if ((correlationId == null) || (correlationId == "")) {
            val newCorrelationId = UUID.randomUUID().toString()
            MDC.put(Slf4jInterceptor.correlationIdLogAttribute, newCorrelationId)
        } else {
            MDC.put(Slf4jInterceptor.correlationIdLogAttribute, correlationId)
        }
    }

}