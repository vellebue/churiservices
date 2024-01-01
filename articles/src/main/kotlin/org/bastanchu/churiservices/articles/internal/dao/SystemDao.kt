package org.bastanchu.churiservices.articles.internal.dao

interface  SystemDao  {

    fun retrievePostgresqlVersion() : String
}