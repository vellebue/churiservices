package org.bastanchu.churiservices.orders.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.bastanchu.churiservices.core.api.model.addresses.Address
import org.bastanchu.churiservices.core.api.model.article.ArticlePricingCondition
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.core.api.model.orders.OrderLinePricingCondition
import org.bastanchu.churiservices.core.api.service.exception.ServiceException
import org.bastanchu.churiservices.coretest.api.test.BaseITCase
import org.bastanchu.churiservices.orders.internal.service.AssessmentOrderService

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import kotlin.test.assertFailsWith

class AssessmentOrderServiceITCase(@Autowired val assessmentOrderService: AssessmentOrderService,
                                   @Autowired val objectMapper: ObjectMapper) : BaseITCase() {

    @Test
    fun `should assess simple order properly`() {
        val classLoader = Thread.currentThread().contextClassLoader
        val stream = classLoader.getResourceAsStream("org/bastanchu/churiservices/orders/service/simpleOrderDataToAsset.json")
        stream.use {
            val order = objectMapper.readValue(it, OrderHeader::class.java)
            val valuedOrder = assessmentOrderService.assessOrder(order)
            assertNotNull(valuedOrder)
            // Test header params
            assertEquals(order.customerOrderId, valuedOrder.customerOrderId)
            assertEquals(order.customerId, valuedOrder.customerId)
            assertEquals(BigDecimal(21600).setScale(0), valuedOrder.baseValue.setScale(0))
            //Test addresses
            assertEqualsAddress(order.deliveryAdress, valuedOrder.deliveryAdress)
            assertEqualsAddress(order.invoiceAddress, valuedOrder.invoiceAddress)
            // Test line
            val valuedLine = valuedOrder.lines[0]
            assertEquals("COMPQ0017", valuedLine.articleId)
            assertEquals(3, valuedLine.numItems)
            assertEquals("CS", valuedLine.formatUnit)
            assertEquals("Compq computer 17''", valuedLine.articleDescription)
            assertEquals(BigDecimal(7200).setScale(0), valuedLine.basePrice.setScale(0))
            assertEquals(BigDecimal(1512).setScale(0), valuedLine.vatTax.setScale(0))
            assertEquals(BigDecimal(21600).setScale(0), valuedLine.baseValue.setScale(0))
            assertEquals(BigDecimal(4536).setScale(0), valuedLine.vatTaxValue.setScale(0))
            assertEquals(BigDecimal(26136).setScale(0), valuedLine.totalValue.setScale(0))
            // Test line value conditions
            val condition1 = valuedLine.conditions[0]
            val condition2 = valuedLine.conditions[1]
            // Test value condition 1
            assertEquals(ArticlePricingCondition.PricingConditionType.FARE, condition1.type)
            assertEquals("", condition1.subtype)
            assertEquals(1, condition1.order)
            assertEquals(BigDecimal(1200).setScale(0), condition1.value.setScale(0))
            assertEquals(ArticlePricingCondition.PricingConditionKind.VALUE, condition1.valueType)
            assertEquals(BigDecimal.ZERO, condition1.baseValue)
            assertEquals(BigDecimal(21600).setScale(0), condition1.conditionValue.setScale(0))
            assertEquals(BigDecimal(21600).setScale(0), condition1.totalValue.setScale(0))
            // Test value condition 2
            assertEquals(ArticlePricingCondition.PricingConditionType.VAT_TAX, condition2.type)
            assertEquals("NR", condition2.subtype)
            assertEquals(90, condition2.order)
            assertEquals(BigDecimal(21).setScale(0), condition2.value.setScale(0))
            assertEquals(ArticlePricingCondition.PricingConditionKind.PERCENTAGE, condition2.valueType)
            assertEquals(BigDecimal(21600).setScale(0), condition2.baseValue.setScale(0))
            assertEquals(BigDecimal(4536).setScale(0), condition2.conditionValue.setScale(0))
            assertEquals(BigDecimal(26136).setScale(0), condition2.totalValue.setScale(0))
        }
    }

    @Test
    fun `should fail when assessing not registered article`() {
        val classLoader = Thread.currentThread().contextClassLoader
        val stream = classLoader.getResourceAsStream("org/bastanchu/churiservices/orders/service/simpleOrderDataToAssetNotRegisteredArticle.json")
        val exception = assertFailsWith<ServiceException> {
            stream.use {
                val order = objectMapper.readValue(it, OrderHeader::class.java)
                val valuedOrder = assessmentOrderService.assessOrder(order)
            }
        }
        assertEquals("Article not registered NOTEXIST", exception.message)
    }

    @Test
    fun `should fail when assessing not valid format for article`() {
        val classLoader = Thread.currentThread().contextClassLoader
        val stream = classLoader.getResourceAsStream("org/bastanchu/churiservices/orders/service/simpleOrderDataToAssetNotRegisteredFormatForArticle.json")
        val exception = assertFailsWith<ServiceException> {
            stream.use {
                val order = objectMapper.readValue(it, OrderHeader::class.java)
                val valuedOrder = assessmentOrderService.assessOrder(order)
            }
        }
        assertEquals("For article COMPQ0017 there is no defined unit BS", exception.message)
    }

    private fun assertEqualsAddress(expectedAddress: Address, actualAddress: Address) {
        assertEquals(expectedAddress.address, actualAddress.address)
        assertEquals(expectedAddress.zipCode, actualAddress.zipCode)
        assertEquals(expectedAddress.city, actualAddress.city)
        assertEquals(expectedAddress.regionId, actualAddress.regionId)
        assertEquals(expectedAddress.regionName, actualAddress.regionName)
        assertEquals(expectedAddress.countryId, actualAddress.countryId)
        assertEquals(expectedAddress.countryName, actualAddress.countryName)
    }
}