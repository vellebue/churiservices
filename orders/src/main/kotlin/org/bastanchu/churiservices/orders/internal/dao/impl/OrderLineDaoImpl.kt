package org.bastanchu.churiservices.orders.internal.dao.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.orders.OrderLine
import org.bastanchu.churiservices.orders.internal.dao.OrderLineDao
import org.bastanchu.churiservices.orders.internal.entity.OrderLineEntity
import org.bastanchu.churiservices.orders.internal.entity.OrderLinePK
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class OrderLineDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager)
    : BaseValueObjectDaoImpl<OrderLinePK, OrderLineEntity, OrderLine>(entityManager, OrderLineMapper())
    , OrderLineDao {

   class OrderLineMapper : BaseValueObjectEntityMapperDefaultImpl<OrderLine, OrderLineEntity>()
}