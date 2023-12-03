package org.bastanchu.churiservices.core.api.model

data class PingStatus(var componentName : String,
                      var version : String,
                      var status: Status,
                      var timestamp: String,
                      var dependencies : List<PingStatus> = ArrayList<PingStatus>()
                      )
{
    enum class Status(val statusName : String) {
        RUNNING("RUNNING"),
        SHUTDOWN("SHUTDOWN")
    }
}