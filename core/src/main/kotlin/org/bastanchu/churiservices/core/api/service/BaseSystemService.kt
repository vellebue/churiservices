package org.bastanchu.churiservices.core.api.service

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.model.security.User

interface BaseSystemService {

    fun getSystemPingStatus() : PingStatus

    fun getFullSystemPingStatus() : PingStatus

    fun getUserDetails() : User
}