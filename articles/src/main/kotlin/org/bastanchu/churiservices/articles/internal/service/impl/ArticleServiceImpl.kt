package org.bastanchu.churiservices.articles.internal.service.impl

import org.bastanchu.churiservices.articles.internal.dao.ArticleDao
import org.bastanchu.churiservices.articles.internal.dao.ArticleFormatDao
import org.bastanchu.churiservices.articles.internal.dao.PricingConditionDao
import org.bastanchu.churiservices.articles.internal.service.ArticleService
import org.bastanchu.churiservices.core.api.model.article.Article

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class ArticleServiceImpl(@Autowired articleDao : ArticleDao,
                         @Autowired articleFormatDao: ArticleFormatDao,
                         @Autowired pricingConditionDao: PricingConditionDao) : ArticleService {
    override fun createArticle(article: Article): Article {
        TODO("Not yet implemented")
    }

    override fun getArticle(articleId: String) {
        TODO("Not yet implemented")
    }
}