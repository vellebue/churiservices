package org.bastanchu.churiservices.articles.internal.dao.impl

import jakarta.persistence.*

import org.bastanchu.churiservices.articles.internal.dao.ArticleDao
import org.bastanchu.churiservices.articles.internal.entity.ArticleEntity
import org.bastanchu.churiservices.core.api.dao.impl.BaseValueObjectDaoImpl
import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.article.Article

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class ArticleDaoImpl(@PersistenceContext(unitName = "entityManagerFactory") override val entityManager: EntityManager) :
                     BaseValueObjectDaoImpl<String, ArticleEntity, Article>(entityManager, ArticleMapper()),
                     ArticleDao {
    class ArticleMapper : BaseValueObjectEntityMapperDefaultImpl<Article, ArticleEntity>() {

    }
}