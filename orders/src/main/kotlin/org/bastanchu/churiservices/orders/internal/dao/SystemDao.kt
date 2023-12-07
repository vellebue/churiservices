package org.bastanchu.churiservices.orders.internal.dao

interface SystemDao {

    fun retrievePostgresqlVersion() : String;

}