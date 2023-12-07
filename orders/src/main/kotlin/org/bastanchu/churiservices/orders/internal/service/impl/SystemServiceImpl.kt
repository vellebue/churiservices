package org.bastanchu.churiservices.orders.internal.service.impl

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.orders.internal.dao.SystemDao
import org.bastanchu.churiservices.orders.internal.service.SystemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.yaml.snakeyaml.internal.Logger
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
}