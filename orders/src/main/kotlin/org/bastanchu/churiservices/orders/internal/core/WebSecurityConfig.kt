package org.bastanchu.churiservices.orders.internal.core

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
class WebSecurityConfig {

    @Bean
    fun securityFilterChain(httpSecurity : HttpSecurity) : SecurityFilterChain {
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