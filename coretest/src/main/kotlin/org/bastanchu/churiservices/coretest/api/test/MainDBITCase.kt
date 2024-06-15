package org.bastanchu.churiservices.coretest.api.test

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

abstract class MainDBITCase(@Autowired protected val applicationContext : ApplicationContext) : BaseITCase() {

    val logger = LoggerFactory.getLogger(MainDBITCase::class.java)

    @Test
    fun `should integration tests be working`() {
        Assertions.assertTrue(true)
    }

    @Test
    fun shouldPostgresqlContainerBeRunning() {
        logger.info("Testing Postgresql container")
        Assertions.assertTrue(postgreSQLContainer.isRunning, "Postgresql container must be running")
        logger.info("Loaded Spring Beans: ")
        val allBeanNames = applicationContext.getBeanDefinitionNames()
        for (beanName in allBeanNames) {
            logger.info("Registered bean ${beanName}")
        }
        logger.info("Spring Beans List completed")
    }

    @Test
    fun shouldDatasourceBeAvailable() {
        Assertions.assertNotNull(dataSource, "DataSource must not be null")
        val connection = dataSource!!.connection
        Assertions.assertNotNull(connection, "Datasource connection must not be null")
        connection.close()
    }

    @Test
    fun shouldTestQueryWorkCorrectly() {
        val sql = "select * from flyway_schema_history"
        val connection = dataSource!!.connection
        connection.use {
            val statement = it.prepareStatement(sql)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    Assertions.assertTrue(it.next(), "There is no data in flyway_schema_history, no scripts loaded")
                }
            }
        }
    }

    abstract fun shouldTestFullSystemPingStatusCorrectly();

}