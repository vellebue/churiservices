package org.bastanchu.churiservices.orders.internal.service

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.service.QueueSenderService

interface OrdersQueueSenderService : QueueSenderService<OrderHeader> {
}