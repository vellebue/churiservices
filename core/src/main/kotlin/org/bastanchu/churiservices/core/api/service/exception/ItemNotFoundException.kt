package org.bastanchu.churiservices.core.api.service.exception

class ItemNotFoundException(val messageException : String) : RuntimeException(messageException) {
}