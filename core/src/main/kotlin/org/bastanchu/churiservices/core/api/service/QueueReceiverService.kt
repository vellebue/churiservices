package org.bastanchu.churiservices.core.api.service

interface QueueReceiverService<T> {

    fun proccessMessage(content : String)

    fun onReceiveMessage(value : T)

}