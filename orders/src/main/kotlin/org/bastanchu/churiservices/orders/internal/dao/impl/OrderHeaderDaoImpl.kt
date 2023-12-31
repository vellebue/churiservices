package org.bastanchu.churiservices.orders.internal.dao.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.orders.internal.dao.OrderHeaderDao
import org.bastanchu.churiservices.orders.internal.entity.OrderHeaderEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class OrderHeaderDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager)
    : BaseValueObjectDaoImpl<Int, OrderHeaderEntity, OrderHeader>(entityManager, OrderHeaderMapper())
    , OrderHeaderDao {
        class OrderHeaderMapper : BaseValueObjectEntityMapperDefaultImpl<OrderHeader, OrderHeaderEntity>()
}