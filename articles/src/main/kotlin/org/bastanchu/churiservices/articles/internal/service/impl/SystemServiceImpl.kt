package org.bastanchu.churiservices.articles.internal.service.impl

import org.bastanchu.churiservices.articles.internal.dao.SystemDao
import org.bastanchu.churiservices.articles.internal.service.SystemService
import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.service.impl.BaseSystemServiceImpl
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class SystemServiceImpl(@Autowired val systemDao : SystemDao,
                        @Autowired val environment : Environment
                       ) :  SystemService,
                            BaseSystemServiceImpl() {

    val logger = LoggerFactory.getLogger(SystemServiceImpl::class.java)

    var localTimestampFormat : DateFormat = SimpleDateFormat("yyyy-MM-dd")
    init {
        localTimestampFormat = SimpleDateFormat(
            environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
    }

    override fun getComponentName(): String {
        return environment.getProperty("org.bastanchu.churiservices.articles.systemName") ?: ""
    }

    override fun getCurrentVersion(): String {
        return environment.getProperty("org.bastanchu.churiservices.articles.systemVersion") ?: ""
    }

    override fun getTimestampFormat(): DateFormat {
        return localTimestampFormat
    }

    override fun getPostgresqlPingStatus(): PingStatus {
        try {
            val dbVersion = systemDao.retrievePostgresqlVersion()
            val pingStatus = PingStatus(
                componentName = environment.getProperty("org.bastanchu.churiservices.articles.dbSystemName") ?: "",
                componentType = PingStatus.ComponentType.POSTGRESQL_DB,
                status = PingStatus.Status.RUNNING,
                timestamp = getTimestampFormat().format(Date()),
                version = dbVersion
            )
            return pingStatus
        } catch (e: Throwable) {
            logger.error("DB Connection failed retrieving Postgresql Version", e)
            val pingStatus = PingStatus(
                componentName = environment.getProperty("org.bastanchu.churiservices.articles.dbSystemName") ?: "",
                componentType = PingStatus.ComponentType.POSTGRESQL_DB,
                status = PingStatus.Status.SHUTDOWN,
                timestamp = getTimestampFormat().format(Date()),
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