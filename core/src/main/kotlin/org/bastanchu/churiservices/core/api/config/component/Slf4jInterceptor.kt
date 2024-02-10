package org.bastanchu.churiservices.core.api.config.component

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception
import java.util.UUID

class Slf4jInterceptor : HandlerInterceptor {

    val correlationIdHeader = "X-Correlation-ID"
    val correlationIdLogAttribute = "correlationId"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val correlationId = getOrGenerateCorrelationId(request)
        MDC.put(correlationIdLogAttribute, correlationId)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        MDC.remove(correlationIdLogAttribute)
    }

    private fun getOrGenerateCorrelationId(request: HttpServletRequest) : String {
        val correlationIdFromHeader = request.getHeader(correlationIdHeader)
        if (correlationIdFromHeader == null) {
            return UUID.randomUUID().toString()
        } else {
            return correlationIdFromHeader.toString()
        }
    }
}