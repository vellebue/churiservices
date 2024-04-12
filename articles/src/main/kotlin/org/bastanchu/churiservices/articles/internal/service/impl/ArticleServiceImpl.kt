package org.bastanchu.churiservices.articles.internal.service.impl

import org.bastanchu.churiservices.articles.internal.dao.ArticleDao
import org.bastanchu.churiservices.articles.internal.dao.ArticleFormatDao
import org.bastanchu.churiservices.articles.internal.dao.FormatDao
import org.bastanchu.churiservices.articles.internal.dao.PricingConditionDao
import org.bastanchu.churiservices.articles.internal.entity.ArticleFormatEntity
import org.bastanchu.churiservices.articles.internal.entity.PricingConditionEntity
import org.bastanchu.churiservices.articles.internal.service.ArticleService
import org.bastanchu.churiservices.articles.internal.service.FormatService
import org.bastanchu.churiservices.articles.internal.service.SystemService
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
                         @Autowired val pricingConditionDao: PricingConditionDao,
                         @Autowired val formatService: FormatService
) : ArticleService {
    override fun createArticle(article: Article): Article {
        val existingArticle = articleDao.getById(article.articleId)
        if (existingArticle == null) {
            // Transfer article format data
            val articleFormatEntities = articleFormatDao.fromValueObjectToEntityList(article.articleFormats)
            articleFormatEntities.forEach { it.articleId = article.articleId }
            val nonExistingArticleFormatIDs = checkExistingArticleFormats(articleFormatEntities)
            if (nonExistingArticleFormatIDs.size > 0) {
                throw ServiceException("Cannot create article ${article.articleId}. " +
                                        "These referred article formats are not registered ${nonExistingArticleFormatIDs}")
            }
            val pricingConditionEntities = pricingConditionDao.fromValueObjectToEntityList(article.articleConditions)
            // Transfer pricing conditions
            pricingConditionEntities.forEach { it.articleId = article.articleId }
            val articleEntity = articleDao.fromValueObjectToEntity(article)
            articleEntity.articleFormats = articleFormatEntities as MutableList<ArticleFormatEntity>
            articleEntity.articlePricingConditions = pricingConditionEntities as MutableList<PricingConditionEntity>
            // Persist whole article
            articleDao.create(articleEntity)
            articleDao.flush()
            return article
        } else {
            throw ServiceException("Article with id ${article.articleId} already exists.")
        }
    }

    /**
     * Given a list of article format entities returns a list of article format ids that
     * are not registered into DB at the moment.
     *
     * @param articleFormatEntities A list of article format entities.
     * @return A listo of article format IDs that are not registered into system DB.
     */
    private fun checkExistingArticleFormats(articleFormatEntities : List<ArticleFormatEntity>) : List<String> {
        val nonExistingArticleFormatIDs = ArrayList<String>()
        val allArticleFormats = formatService.getFormats()
        val allArticleFormatsIDs = allArticleFormats.map { it.formatId }
        articleFormatEntities.forEach {
            if (!allArticleFormatsIDs.contains(it.formatId)) {
                nonExistingArticleFormatIDs.add(it.formatId!!)
            }
        }
        return nonExistingArticleFormatIDs
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