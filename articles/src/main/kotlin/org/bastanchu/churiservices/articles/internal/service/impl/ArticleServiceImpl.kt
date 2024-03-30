package org.bastanchu.churiservices.articles.internal.service.impl

import org.bastanchu.churiservices.articles.internal.dao.ArticleDao
import org.bastanchu.churiservices.articles.internal.dao.ArticleFormatDao
import org.bastanchu.churiservices.articles.internal.dao.FormatDao
import org.bastanchu.churiservices.articles.internal.dao.PricingConditionDao
import org.bastanchu.churiservices.articles.internal.entity.ArticleFormatEntity
import org.bastanchu.churiservices.articles.internal.entity.PricingConditionEntity
import org.bastanchu.churiservices.articles.internal.service.ArticleService
import org.bastanchu.churiservices.core.api.model.article.Article
import org.bastanchu.churiservices.core.api.service.exception.ServiceException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class ArticleServiceImpl(@Autowired val articleDao : ArticleDao,
                         @Autowired val articleFormatDao: ArticleFormatDao,
                         @Autowired val pricingConditionDao: PricingConditionDao
) : ArticleService {
    override fun createArticle(article: Article): Article {
        val existingArticle = articleDao.getById(article.articleId)
        if (existingArticle == null) {
            val articleFormatEntities = articleFormatDao.fromValueObjectToEntityList(article.articleFormats)
            articleFormatEntities.forEach { it.articleId = article.articleId }
            val pricingConditionEntities = pricingConditionDao.fromValueObjectToEntityList(article.articleConditions)
            pricingConditionEntities.forEach { it.articleId = article.articleId }
            val articleEntity = articleDao.fromValueObjectToEntity(article)
            articleEntity.articleFormats = articleFormatEntities as MutableList<ArticleFormatEntity>
            articleEntity.articlePricingConditions = pricingConditionEntities as MutableList<PricingConditionEntity>
            articleDao.create(articleEntity)
            articleDao.flush()
            return article
        } else {
            throw ServiceException("Article with id ${article.articleId} already exists.")
        }
    }

    override fun getArticle(articleId: String) : Article? {
        var articleEntity = articleDao.getById(articleId)
        if (articleEntity != null) {
            val article = articleDao.toValueObject(articleEntity)
            article.articleConditions = pricingConditionDao.toValueObjectList(articleEntity.articlePricingConditions)
            article.articleFormats = articleFormatDao.toValueObjectList(articleEntity.articleFormats)
            return article
        } else {
            return null
        }
    }

    override fun getArticles(): List<Article> {
        val articleEntities = articleDao.listAll()
        val articles = articleDao.toValueObjectList(articleEntities)
        return articles
    }
}