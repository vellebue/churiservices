package org.bastanchu.churiservices.deliveries.internal.handler

import org.bastanchu.churiservices.core.api.config.handler.GlobalExceptionHandler
import java.net.URI

class DeliveriesExceptionHandler : GlobalExceptionHandler() {

    override fun getTypeURI(): URI {
        return URI.create("/deliveriesService")
    }

}