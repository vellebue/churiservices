package org.bastanchu.churiservices.orders.internal.service

import org.bastanchu.churiservices.core.api.model.PingStatus

interface SystemService {

    fun getSystemPingStatus() : PingStatus

    fun getPostgresqlPingStatus() : PingStatus

    fun getFullSystemPingStatus() : PingStatus

}