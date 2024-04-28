package org.bastanchu.churiservices.orders.internal.service.impl

import org.bastanchu.churiservices.core.api.model.article.Article
import org.bastanchu.churiservices.core.api.model.article.ArticlePricingCondition
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.model.orders.OrderLine
import org.bastanchu.churiservices.core.api.model.orders.OrderLinePricingCondition
import org.bastanchu.churiservices.core.api.service.BaseSystemService
import org.bastanchu.churiservices.core.api.service.exception.ServiceException
import org.bastanchu.churiservices.orders.internal.service.ArticleService
import org.bastanchu.churiservices.orders.internal.service.AssessmentOrderService
import org.bastanchu.churiservices.orders.internal.service.SystemService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class AssessmentOrderServiceImpl(@Autowired val articleService: ArticleService) : AssessmentOrderService {

    val logger = LoggerFactory.getLogger(AssessmentOrderServiceImpl::class.java)
    override fun assessOrder(orderHeader: OrderHeader): OrderHeader {
        val orderLines = orderHeader.lines
        orderLines.forEach {
            assessLine(it)
            //val lastLine = it.conditions[it.conditions.size - 1]
            val vatTaxLine = it.conditions.filter {
                                    it.type == ArticlePricingCondition.PricingConditionType.VAT_TAX
                             }[0]
            it.basePrice = vatTaxLine.baseValue.divide(BigDecimal(it.numItems)).setScale(3, RoundingMode.HALF_EVEN)
            it.baseValue = vatTaxLine.baseValue.setScale(3, RoundingMode.HALF_EVEN)
            it.vatTax = vatTaxLine.conditionValue.divide(BigDecimal(it.numItems)).setScale(3, RoundingMode.HALF_EVEN)
            it.vatTaxValue = vatTaxLine.conditionValue.setScale(3, RoundingMode.HALF_EVEN)
            it.totalPrice = vatTaxLine.totalValue.divide(BigDecimal(it.numItems)).setScale(3, RoundingMode.HALF_EVEN)
            it.totalValue = vatTaxLine.totalValue
        }
        orderHeader.baseValue = orderHeader.lines.map { it.baseValue }
                                .reduce {acc,value -> acc.plus(value)}
                                .setScale(2, RoundingMode.HALF_EVEN)
        orderHeader.vatTaxValue = orderHeader.lines.map { it.vatTaxValue }
                                  .reduce {acc,value -> acc.plus(value)}
                                  .setScale(2, RoundingMode.HALF_EVEN)
        orderHeader.totalValue = orderHeader.lines.map { it.totalValue }
                                 .reduce {acc,value -> acc.plus(value)}
                                 .setScale(2, RoundingMode.HALF_EVEN)
        return orderHeader
    }

    private fun assessLine(orderLine : OrderLine) {
        val article = articleService.getArticle(orderLine.articleId)
        if (article != null) {
            val numUnits = getNumUnits(orderLine, article)
            orderLine.articleDescription = article.description
            assessLineArticleCondition(numUnits,
                                       orderLine.conditions as MutableList<OrderLinePricingCondition>,
                                       article.articleConditions)
        } else {
            throw ServiceException("Article not registered ${orderLine.articleId}")
        }
    }

    private fun getNumUnits(orderLine : OrderLine, article : Article) : Int {
        val articleFormatList = article.articleFormats.filter { it.formatId == orderLine.formatUnit }
        if (articleFormatList.size > 0) {
            val articleFormat = articleFormatList[0]
            val numUnits = articleFormat.conversionFactor.multiply(BigDecimal(orderLine.numItems))
                .intValueExact()
            return numUnits
        } else {
            throw ServiceException("For article ${article.articleId} there is no defined unit ${orderLine.formatUnit}")
        }
    }

    private fun assessLineArticleCondition(numUnits : Int,
                                           linePricingConditions : MutableList<OrderLinePricingCondition>,
                                           articlePricingConditions : List<ArticlePricingCondition>) {
        val sortedArticlePricingConditions = articlePricingConditions.sortedBy { it.order }
        sortedArticlePricingConditions.forEach {
            val type = it.type
            val subtype = it.subtype
            val order = it.order
            val valueType = it.valueType
            val value = it.value
            val previousMinorCondition = getPreviousMinorPricingCondition(order, linePricingConditions)
            val prevousCondition = getPreviousPricingCondition(linePricingConditions)
            var baseValue = previousMinorCondition?.totalValue ?: BigDecimal.ZERO
            var conditionValue = BigDecimal.ZERO
            if (valueType == ArticlePricingCondition.PricingConditionKind.PERCENTAGE) {
                conditionValue = baseValue.multiply(value).divide(BigDecimal(100.0)).setScale(3, RoundingMode.HALF_EVEN)
            } else {
                conditionValue = value.multiply(BigDecimal(numUnits)).setScale(3, RoundingMode.HALF_EVEN)
            }
            val totalValue = prevousCondition?.totalValue
                             ?.plus(conditionValue)
                             ?.setScale(3, RoundingMode.HALF_EVEN) ?: conditionValue
            linePricingConditions.add(
                OrderLinePricingCondition(type, subtype, order, valueType, value,
                baseValue, conditionValue, totalValue)
            )
        }
    }

    private fun getPreviousMinorPricingCondition(referenceOrder : Int,
                                                 linePricingConditions: List<OrderLinePricingCondition>) : OrderLinePricingCondition? {
        var orderLinePricingCondition : OrderLinePricingCondition? = null
        if (linePricingConditions.size > 0) {
            var index = linePricingConditions.size - 1
            while((index >= 0) && (orderLinePricingCondition == null)) {
                val currentOrderPricingCondition = linePricingConditions[index]
                if (currentOrderPricingCondition.order < referenceOrder) {
                    orderLinePricingCondition = currentOrderPricingCondition
                }
                index--
            }
        }
        return orderLinePricingCondition
    }

    private fun getPreviousPricingCondition(linePricingConditions: List<OrderLinePricingCondition>) : OrderLinePricingCondition? {
        if (linePricingConditions.size > 0) {
            return linePricingConditions[linePricingConditions.size - 1]
        } else {
            return null
        }
    }
}