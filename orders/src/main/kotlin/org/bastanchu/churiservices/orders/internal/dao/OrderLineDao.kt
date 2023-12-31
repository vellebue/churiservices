package org.bastanchu.churiservices.orders.internal.dao

import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.orders.OrderLine
import org.bastanchu.churiservices.orders.internal.entity.OrderLineEntity
import org.bastanchu.churiservices.orders.internal.entity.OrderLinePK

interface OrderLineDao : BaseValueObjectDao<OrderLinePK, OrderLineEntity, OrderLine> {
}