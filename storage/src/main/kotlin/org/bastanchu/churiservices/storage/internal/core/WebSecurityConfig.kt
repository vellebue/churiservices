package org.bastanchu.churiservices.storage.internal.core

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(httpSecurity : HttpSecurity) : SecurityFilterChain {
        httpSecurity.cors { it.disable() }
        httpSecurity.csrf { it.disable() }
        httpSecurity.authorizeHttpRequests {
            it.requestMatchers("/**").permitAll()
        }
        return httpSecurity.build()
    }
}