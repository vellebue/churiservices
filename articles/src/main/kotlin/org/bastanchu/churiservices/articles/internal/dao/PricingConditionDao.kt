package org.bastanchu.churiservices.articles.internal.dao

import org.bastanchu.churiservices.articles.internal.entity.PricingConditionEntity
import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.article.ArticlePricingCondition

interface PricingConditionDao : BaseValueObjectDao<Int, PricingConditionEntity, ArticlePricingCondition> {
}