package org.bastanchu.churiservices.core.api.service

interface QueueSenderService<T> {

    fun send(rountingKey : String, value: T)

    fun send(value: T)

}