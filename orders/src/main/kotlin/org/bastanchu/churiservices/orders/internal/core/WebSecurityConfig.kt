package org.bastanchu.churiservices.orders.internal.core

import org.bastanchu.churiservices.core.api.BaseWebSecurityConfig
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer.JwtConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurityConfig : BaseWebSecurityConfig() {

    @Bean
    override fun securityFilterChain(httpSecurity : HttpSecurity) : SecurityFilterChain {
        return super.securityFilterChain(httpSecurity)
    }
}