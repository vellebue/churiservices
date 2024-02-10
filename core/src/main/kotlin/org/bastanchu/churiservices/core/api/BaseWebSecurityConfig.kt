package org.bastanchu.churiservices.core.api

import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

open class BaseWebSecurityConfig {

    open fun securityFilterChain(httpSecurity : HttpSecurity) : SecurityFilterChain {
        httpSecurity.cors { it.disable() }
        httpSecurity.csrf { it.disable() }
        httpSecurity.authorizeHttpRequests {
            it.requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v*/api-docs/**").permitAll()
                    .anyRequest().authenticated()
        }
        httpSecurity.sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
        httpSecurity.oauth2ResourceServer { it.jwt(Customizer.withDefaults()) }
        return httpSecurity.build()
    }
}