package org.bastanchu.churiservices.articles

import org.bastanchu.churiservices.articles.internal.service.SystemService
import org.bastanchu.churiservices.coretest.api.test.MainDBITCase
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.core.env.Environment
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ArticlesDBITCase(@Autowired val environment: Environment,
                       @Autowired val thisApplicationContext : ApplicationContext,
                       @Autowired val systemService : SystemService
) : MainDBITCase(thisApplicationContext) {

    @Test
    override fun shouldTestFullSystemPingStatusCorrectly() {
        val fullPingStatus = systemService.getFullSystemPingStatus()
        assertNotNull(fullPingStatus)
        // Main heading parameters
        val componentName = fullPingStatus.componentName
        assertEquals(retrieveComponentName(), componentName)
        val version = fullPingStatus.version
        assertEquals(retrieveSystemVersion(), version)
        assertEquals("RUNNING", fullPingStatus.status.statusName)
        // System database
        val dbDependency = fullPingStatus.dependencies[0]
        assertNotNull(dbDependency)
        val dbSystemName = dbDependency.componentName
        assertEquals(dbSystemName, dbDependency.componentName)
        assertEquals("RUNNING", dbDependency.status.statusName)
    }

    fun retrieveComponentName() : String {
        return environment.getProperty("org.bastanchu.churiservices.articles.systemName") ?: ""
    }

    fun retrieveSystemVersion() : String {
        return environment.getProperty("org.bastanchu.churiservices.articles.systemVersion") ?: ""
    }

    fun retrieveComponentDbName() : String {
        return environment.getProperty("org.bastanchu.churiservices.articles.dbSystemName") ?: ""
    }
}