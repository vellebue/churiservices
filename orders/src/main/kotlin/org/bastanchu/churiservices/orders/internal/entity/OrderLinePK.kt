package org.bastanchu.churiservices.orders.internal.entity

import java.io.Serializable

data class OrderLinePK(var orderId : Int = 0, var lineId : Int = 0) : Serializable {
}