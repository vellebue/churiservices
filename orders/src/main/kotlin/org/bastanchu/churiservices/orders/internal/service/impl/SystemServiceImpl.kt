package org.bastanchu.churiservices.orders.internal.service.impl

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.model.security.Role
import org.bastanchu.churiservices.core.api.model.security.Token
import org.bastanchu.churiservices.core.api.model.security.User
import org.bastanchu.churiservices.orders.internal.dao.SystemDao
import org.bastanchu.churiservices.orders.internal.service.SystemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.yaml.snakeyaml.internal.Logger
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.log

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class SystemServiceImpl (@Autowired val systemDao : SystemDao,
                         @Autowired val environment : Environment) : SystemService {

    val logger = LoggerFactory.getLogger(SystemServiceImpl::class.java)
    var timestampFormat : DateFormat = SimpleDateFormat("yyyy-MM-dd")
    init {
        timestampFormat = SimpleDateFormat(
            environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
    }
    override fun getSystemPingStatus(): PingStatus {
        val pingStatus = PingStatus(
            componentName = environment.getProperty("org.bastanchu.churiservices.orders.systemName") ?: "",
            componentType = PingStatus.ComponentType.SPRING_BOOT_APP,
            status = PingStatus.Status.RUNNING,
            timestamp = timestampFormat.format(Date()),
            version = environment.getProperty("org.bastanchu.churiservices.orders.systemVersion") ?: "")
        return pingStatus
    }

    override fun getPostgresqlPingStatus(): PingStatus {
        try {
            val dbVersion = systemDao.retrievePostgresqlVersion()
            val pingStatus = PingStatus(
                componentName = environment.getProperty("org.bastanchu.churiservices.orders.dbSystemName") ?: "",
                componentType = PingStatus.ComponentType.POSTGRESQL_DB,
                status = PingStatus.Status.RUNNING,
                timestamp = timestampFormat.format(Date()),
                version = dbVersion
            )
            return pingStatus
        } catch (e: Throwable) {
            logger.error("DB Connection failed retrieving Postgresql Version", e)
            val pingStatus = PingStatus(
                componentName = environment.getProperty("org.bastanchu.churiservices.orders.dbSystemName") ?: "",
                componentType = PingStatus.ComponentType.POSTGRESQL_DB,
                status = PingStatus.Status.SHUTDOWN,
                timestamp = timestampFormat.format(Date()),
                version = ""
            )
            return pingStatus
        }
    }

    override fun getFullSystemPingStatus(): PingStatus {
        val pingStatus = getSystemPingStatus()
        val dbPingStatus = getPostgresqlPingStatus()
        pingStatus.dependencies.add(dbPingStatus)
        if (dbPingStatus.status != PingStatus.Status.RUNNING) {
            pingStatus.status = PingStatus.Status.NOT_AVAILABLE
        }
        return pingStatus
    }

    override fun getUserDetails(): User {
        val auth = SecurityContextHolder.getContext().getAuthentication()
        val principal = auth.principal as Jwt
        val claims = principal.claims
        var userType : User.Type? = null
        var email = ""
        // General data
        var login = ""
        var name : String? = null
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
        val issuedAt = timestampFormat.format(Timestamp.from(principal.issuedAt))
        val expiresAt = timestampFormat.format(Timestamp.from(principal.expiresAt))
        val tokenValue = principal.tokenValue
        val clientIssuer = claims.get("azp") as String
        val tokenType = claims.get("typ") as String
        val token = Token(type = tokenType, token = tokenValue, issueTimestamp = issuedAt, expiryTimestamp = expiresAt, issuer = clientIssuer)
        return User(login = login, name = name, email = email, type = userType, token = token, roles = targetRolesList)
    }
}