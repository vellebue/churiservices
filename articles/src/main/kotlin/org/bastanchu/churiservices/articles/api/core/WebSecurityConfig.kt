package org.bastanchu.churiservices.articles.api.core

import org.bastanchu.churiservices.core.api.BaseWebSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurityConfig : BaseWebSecurityConfig() {

    @Bean
    override fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return super.securityFilterChain(httpSecurity)
    }
}