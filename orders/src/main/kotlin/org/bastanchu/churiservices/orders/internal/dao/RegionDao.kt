package org.bastanchu.churiservices.orders.internal.dao

import org.bastanchu.churiservices.core.api.dao.BaseDao
import org.bastanchu.churiservices.orders.internal.entity.RegionEntity
import org.bastanchu.churiservices.orders.internal.entity.RegionPK

interface RegionDao : BaseDao<RegionPK, RegionEntity> {
}