package org.bastanchu.churiservices.articles.internal.dao.impl

import org.bastanchu.churiservices.articles.internal.dao.SystemDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import javax.sql.DataSource

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class SystemDaoImpl(@Autowired val dataSource : DataSource) : SystemDao {

    override fun retrievePostgresqlVersion(): String {
        val sql = "show server_version";
        val con = dataSource.connection
        con.use {
            val statement = it.prepareStatement(sql)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    if (it.next()) {
                        val version = it.getString(1)
                        return version
                    }
                }
            }
        }
        return ""
    }
}