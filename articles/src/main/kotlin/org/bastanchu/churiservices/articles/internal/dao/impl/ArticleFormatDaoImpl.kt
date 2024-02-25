package org.bastanchu.churiservices.articles.internal.dao.impl

import jakarta.persistence.*
import org.bastanchu.churiservices.articles.internal.dao.ArticleFormatDao
import org.bastanchu.churiservices.articles.internal.entity.ArticleFormatEntity
import org.bastanchu.churiservices.articles.internal.entity.ArticleFormatPK
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.article.ArticleFormat

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional



@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class ArticleFormatDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager) :
                             BaseValueObjectDaoImpl<ArticleFormatPK,  ArticleFormatEntity, ArticleFormat>(entityManager, ArticleFormatMapper()),
                             ArticleFormatDao {
    class ArticleFormatMapper : BaseValueObjectEntityMapperDefaultImpl<ArticleFormat, ArticleFormatEntity>() {
    }
}