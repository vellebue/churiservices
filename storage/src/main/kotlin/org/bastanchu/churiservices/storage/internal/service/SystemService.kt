package org.bastanchu.churiservices.storage.internal.service

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.service.BaseSystemService

interface SystemService : BaseSystemService {

    fun getPostgresqlPingStatus() : PingStatus
}