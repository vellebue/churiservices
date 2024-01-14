package org.bastanchu.churiservices.orders.internal.entity

import java.io.Serializable

data class OrderLinePK(var orderId : Int? = null, var lineId : Int? = null) : Serializable {
}