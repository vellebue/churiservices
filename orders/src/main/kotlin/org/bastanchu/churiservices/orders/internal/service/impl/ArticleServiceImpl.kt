package org.bastanchu.churiservices.orders.internal.service.impl

import org.bastanchu.churiservices.core.api.config.component.Slf4jInterceptor
import org.bastanchu.churiservices.core.api.model.article.Article
import org.bastanchu.churiservices.orders.internal.service.ArticleService
import org.bastanchu.churiservices.orders.internal.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class ArticleServiceImpl(@Autowired val environment : Environment,
                         @Autowired val systemService: SystemService) : ArticleService {

    var restClient = RestClient.create(environment.getProperty("org.bastanchu.churiservices.articles.baseURL")!!)
    init {


    }
    override fun getArticle(articleId: String): Article? {
        val token = systemService.getTokenInUse()
        val correlationId = systemService.getCurrentCorrelationId()
        val authorization = "${token.type} ${token.token}"
        val article = restClient.get()
                                .uri("/articles/article/${articleId}")
                                .header("Authorization", authorization)
                                .header(Slf4jInterceptor.correlationIdHeader, correlationId)
                                .retrieve()
                                .body(Article::class.java) ?: null
        return article
    }
}