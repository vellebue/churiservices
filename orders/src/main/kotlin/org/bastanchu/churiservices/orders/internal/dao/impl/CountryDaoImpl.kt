package org.bastanchu.churiservices.orders.internal.dao.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.bastanchu.churiservices.core.api.dao.impl.BaseDaoImpl
import org.bastanchu.churiservices.orders.internal.dao.CountryDao
import org.bastanchu.churiservices.orders.internal.entity.CountryEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class CountryDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager)
    : BaseDaoImpl<String, CountryEntity>(entityManager)
    , CountryDao {
}