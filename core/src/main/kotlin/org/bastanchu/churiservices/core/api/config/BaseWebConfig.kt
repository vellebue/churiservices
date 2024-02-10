package org.bastanchu.churiservices.core.api.config

import org.bastanchu.churiservices.core.api.config.component.Slf4jInterceptor
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

open class BaseWebConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(Slf4jInterceptor())
    }
}