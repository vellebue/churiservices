package org.bastanchu.churiservices.orders.internal.service

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.model.security.User

interface SystemService {

    fun getSystemPingStatus() : PingStatus

    fun getPostgresqlPingStatus() : PingStatus

    fun getFullSystemPingStatus() : PingStatus

    fun getUserDetails() : User

}