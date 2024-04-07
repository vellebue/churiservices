package org.bastanchu.churiservices.core.api.service.impl

import org.bastanchu.churiservices.core.api.config.component.Slf4jInterceptor
import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.model.security.Role
import org.bastanchu.churiservices.core.api.model.security.Token
import org.bastanchu.churiservices.core.api.model.security.User
import org.bastanchu.churiservices.core.api.service.BaseSystemService
import org.slf4j.MDC
import org.springframework.core.env.Environment
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.client.RestClient
import java.sql.Timestamp
import java.text.DateFormat
import java.util.*

abstract class BaseSystemServiceImpl() : BaseSystemService {

    abstract fun getComponentName(): String

    abstract fun getCurrentVersion(): String

    abstract fun getTimestampFormat(): DateFormat

    override fun getSystemPingStatus(): PingStatus {
        val pingStatus = PingStatus(
            componentName = getComponentName(),
            componentType = PingStatus.ComponentType.SPRING_BOOT_APP,
            status = PingStatus.Status.RUNNING,
            timestamp = getTimestampFormat().format(Date()),
            version = getCurrentVersion()
        )
        return pingStatus
    }

    override fun getUserDetails(): User {
        val auth = SecurityContextHolder.getContext().getAuthentication()
        val principal = auth.principal as Jwt
        val claims = principal.claims
        var userType: User.Type? = null
        var email = ""
        // General data
        var login = ""
        var name: String? = null
        if (claims.get("name") != null) {
            login = claims.get("preferred_username") as String
            name = claims.get("name") as String
            userType = User.Type.REGULAR_USER
            email = claims.get("email") as String
        } else {
            login = claims.get("client_id") as String
            userType = User.Type.SYSTEM_USER
            name = ""
        }
        // Roles list
        val realmAccess = claims.get("realm_access") as Map<String, List<String>>
        val rolesList = realmAccess.get("roles")!!
        val targetRolesList = rolesList.map { Role(it, it) }
        // Token
        val issuedAt = getTimestampFormat().format(Timestamp.from(principal.issuedAt))
        val expiresAt = getTimestampFormat().format(Timestamp.from(principal.expiresAt))
        val tokenValue = principal.tokenValue
        val clientIssuer = claims.get("azp") as String
        val tokenType = claims.get("typ") as String
        val token = Token(
            type = tokenType,
            token = tokenValue,
            issueTimestamp = issuedAt,
            expiryTimestamp = expiresAt,
            issuer = clientIssuer
        )
        return User(login = login, name = name, email = email, type = userType, token = token, roles = targetRolesList)
    }

    override fun getTokenInUse(): Token {
        val user = getUserDetails()
        return user.token
    }

    override fun getCurrentCorrelationId(): String {
        val correlationId = MDC.get(Slf4jInterceptor.correlationIdHeader) ?: ""
        return correlationId
    }
}