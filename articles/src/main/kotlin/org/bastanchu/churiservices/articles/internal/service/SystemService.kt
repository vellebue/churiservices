package org.bastanchu.churiservices.articles.internal.service

import org.bastanchu.churiservices.core.api.model.PingStatus

interface SystemService {

    fun getSystemPingStatus() : PingStatus

    fun getPostgresqlPingStatus() : PingStatus

    fun getFullSystemPingStatus() : PingStatus

}