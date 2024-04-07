package org.bastanchu.churiservices.core.api.config.component

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import java.util.UUID

class Slf4jInterceptor : HandlerInterceptor {

    companion object {
        val correlationIdHeader = "X-CORRELATION-ID"
        val transactionIdHeader = "X-TRANSACTION-ID"
    }

    val correlationIdLogAttribute = "correlationId"
    val transactionIdLogAttribute = "transactionId"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val correlationId = getOrGenerateCorrelationId(request)
        val transactionId = getTransactionId()
        response.setHeader(correlationIdHeader, correlationId)
        response.setHeader(transactionIdHeader, transactionId)
        MDC.put(correlationIdLogAttribute, correlationId)
        MDC.put(transactionIdLogAttribute, transactionId)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        MDC.remove(correlationIdLogAttribute)
        MDC.remove(transactionIdLogAttribute)
    }

    private fun getOrGenerateCorrelationId(request: HttpServletRequest) : String {
        val correlationIdFromHeader = request.getHeader(correlationIdHeader)
        if (correlationIdFromHeader == null) {
            return UUID.randomUUID().toString()
        } else {
            return correlationIdFromHeader.toString()
        }
    }

    private fun getTransactionId() : String {
        return UUID.randomUUID().toString();
    }
}