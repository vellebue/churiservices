package org.bastanchu.churiservices.orders.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.bastanchu.churiservices.core.api.model.article.Article
import org.bastanchu.churiservices.orders.internal.service.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service

@Service
@ConditionalOnProperty(name = arrayOf("org.bastanchu.churiservices.executionMode"), havingValue = "test")
class ArticleServiceMockImpl(@Autowired val objectMapper : ObjectMapper) : ArticleService {

    val articlesMap = HashMap<String, Article>() as MutableMap<String, Article>

    init {
        val classLoader = Thread.currentThread().contextClassLoader
        val stream = classLoader.getResourceAsStream("org/bastanchu/churiservices/orders/service/allArticlesList.json")
        stream.use {
            val articles = objectMapper.readValue(it, Array<Article>::class.java)
            articles.forEach {
                articlesMap.put(it.articleId, it)
            }
        }
    }
    override fun getArticle(articleId: String): Article? {
        return articlesMap.get(articleId)
    }
}