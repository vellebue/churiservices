package org.bastanchu.churiservices.orders.internal.dao

import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.addresses.Address
import org.bastanchu.churiservices.orders.internal.entity.AddressEntity

interface AddressDao : BaseValueObjectDao<Int, AddressEntity, Address> {
}