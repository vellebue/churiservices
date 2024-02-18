package org.bastanchu.churiservices.articles.internal.dao.impl

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.bastanchu.churiservices.articles.internal.dao.FormatDao
import org.bastanchu.churiservices.articles.internal.entity.FormatEntity
import org.bastanchu.churiservices.core.api.dao.impl.BaseDaoImpl
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.article.Format
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class FormatDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager) :
                      BaseValueObjectDaoImpl<String, FormatEntity, Format>(entityManager, FormatMapper()),
                      FormatDao {
      class FormatMapper : BaseValueObjectEntityMapperDefaultImpl<Format, FormatEntity>() {
      }
}