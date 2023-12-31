package org.bastanchu.churiservices.orders.internal.dao

import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.orders.internal.entity.OrderHeaderEntity

interface OrderHeaderDao : BaseValueObjectDao<Int, OrderHeaderEntity, OrderHeader> {
}