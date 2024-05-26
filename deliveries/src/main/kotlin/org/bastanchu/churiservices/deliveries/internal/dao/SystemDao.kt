package org.bastanchu.churiservices.deliveries.internal.dao

interface SystemDao {

    fun retrievePostgresqlVersion() : String
}