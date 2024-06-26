package org.bastanchu.churiservices.deliveries.api.controller

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.model.security.User
import org.bastanchu.churiservices.deliveries.internal.service.SystemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingController(@Autowired val systemService : SystemService) {

    val logger = LoggerFactory.getLogger(PingController::class.java)!!

    @GetMapping("/ping")
    fun ping() : PingStatus {
        logger.info("Ping received")
        val status = systemService.getSystemPingStatus()
        return status
    }

    @GetMapping("/ping/full")
    fun pingFull() : ResponseEntity<PingStatus> {
        logger.info("Ping full received")
        val status = systemService.getFullSystemPingStatus()
        if (status.status != PingStatus.Status.RUNNING) {
            return ResponseEntity(status, HttpStatus.SERVICE_UNAVAILABLE)
        } else {
            return ResponseEntity(status, HttpStatus.OK)
        }
    }

    @GetMapping("/user")
    fun getUser() : User {
        logger.info("User info requested")
        return systemService.getUserDetails()
    }
}