package org.bastanchu.churiservices.orders.internal.service

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader

interface AssessmentOrderService {

    fun assessOrder(orderHeader: OrderHeader) : OrderHeader
}