package org.bastanchu.churiservices.storage.internal.dao

interface SystemDao {

    fun retrievePostgresqlVersion() : String;

}