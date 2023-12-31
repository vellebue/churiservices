package org.bastanchu.churiservices.orders.internal.service

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader

interface OrderService {

    fun createOrder(order : OrderHeader)

    fun getOrder(orderId : Int) : OrderHeader?

}