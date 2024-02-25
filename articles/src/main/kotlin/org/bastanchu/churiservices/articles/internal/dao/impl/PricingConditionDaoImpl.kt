package org.bastanchu.churiservices.articles.internal.dao.impl

import jakarta.persistence.*
import org.bastanchu.churiservices.articles.internal.dao.PricingConditionDao
import org.bastanchu.churiservices.articles.internal.entity.PricingConditionEntity
import org.bastanchu.churiservices.articles.internal.entity.PricingCondtionPK
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.article.ArticlePricingCondition

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class PricingConditionDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager) :
                                BaseValueObjectDaoImpl<PricingCondtionPK, PricingConditionEntity, ArticlePricingCondition> (entityManager, PricingConditionMapper()),
                                PricingConditionDao {

    class PricingConditionMapper : BaseValueObjectEntityMapperDefaultImpl<ArticlePricingCondition, PricingConditionEntity>() {
    }
}