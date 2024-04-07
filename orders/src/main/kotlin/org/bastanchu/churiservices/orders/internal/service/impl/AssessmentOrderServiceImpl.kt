package org.bastanchu.churiservices.orders.internal.service.impl

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.service.BaseSystemService
import org.bastanchu.churiservices.orders.internal.service.ArticleService
import org.bastanchu.churiservices.orders.internal.service.AssessmentOrderService
import org.bastanchu.churiservices.orders.internal.service.SystemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class AssessmentOrderServiceImpl(@Autowired val articleService: ArticleService) : AssessmentOrderService {

    val logger = LoggerFactory.getLogger(AssessmentOrderServiceImpl::class.java)
    override fun assessOrder(orderHeader: OrderHeader): OrderHeader {
        val orderLines = orderHeader.lines
        orderLines.forEach {
            val article = articleService.getArticle(it.articleId)
            logger.info("Article ${article?.articleId} with value ${article?.articleConditions?.get(0)?.value}")
        }
        return orderHeader
    }
}