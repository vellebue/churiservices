package org.bastanchu.churiservices.deliveries.internal.service

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.service.QueueReceiverService

interface OrdersQueueReceiverService : QueueReceiverService<OrderHeader> {
}