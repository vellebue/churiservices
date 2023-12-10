package org.bastanchu.churiservices.core.api.mapper

import org.bastanchu.churiservices.core.api.model.PingStatus

data class PingStatusTestEntity (

    var componentName : String? = null,

    var componentType : PingStatus.ComponentType? = null,

    var version : String? = null,

    var status: PingStatus.Status? = null,

    var timestamp: String? = null,

    var dependencies : MutableList<PingStatus> = ArrayList<PingStatus>()
)