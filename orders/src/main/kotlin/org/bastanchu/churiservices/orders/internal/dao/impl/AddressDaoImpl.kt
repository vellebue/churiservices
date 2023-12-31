package org.bastanchu.churiservices.orders.internal.dao.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.addresses.Address
import org.bastanchu.churiservices.orders.internal.dao.AddressDao
import org.bastanchu.churiservices.orders.internal.entity.AddressEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class AddressDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager)
    : BaseValueObjectDaoImpl<Int, AddressEntity, Address>(entityManager, AddressMapper())
    , AddressDao {

    class AddressMapper : BaseValueObjectEntityMapperDefaultImpl<Address, AddressEntity>() {

    }
}