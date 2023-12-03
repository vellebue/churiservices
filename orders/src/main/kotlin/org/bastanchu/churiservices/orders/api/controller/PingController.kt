package org.bastanchu.churiservices.orders.api.controller

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Date

@RestController
class PingController {

    @GetMapping("/ping")
    fun ping(): PingStatus {
        val status = PingStatus(componentName = "churiservices-order",
                                version = "1.0-status",
                                status = PingStatus.Status.RUNNING,
                                timestamp = Date().toString())
        return status
    }
}