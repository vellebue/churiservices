package org.bastanchu.churiservices.orders.api.controller

import org.bastanchu.churiservices.core.api.model.PingStatus
import org.bastanchu.churiservices.core.api.model.security.User
import org.bastanchu.churiservices.orders.internal.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

/**
 * See base URL to swagger API:
 * http://localhost:8080/swagger-ui/index.html
 */
@RestController
class PingController(@Autowired val systemService : SystemService) {

    @GetMapping("/ping")
    fun ping(): PingStatus {
        val status = systemService.getSystemPingStatus()
        return status
    }

    @GetMapping("/ping/full")
    fun  pingFull(): ResponseEntity<PingStatus> {
        val status = systemService.getFullSystemPingStatus()
        if (status.status != PingStatus.Status.RUNNING) {
            return ResponseEntity(status, HttpStatus.SERVICE_UNAVAILABLE)
        } else {
            return ResponseEntity(status, HttpStatus.OK)
        }
    }

    @GetMapping("/user")
    fun userInfo() : User {
        val user = systemService.getUserDetails()
        return user
    }
}