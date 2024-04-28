package org.bastanchu.churiservices.core.api.model.orders

import org.bastanchu.churiservices.core.api.model.article.ArticlePricingCondition
import java.math.BigDecimal

data class OrderLinePricingCondition(var type : ArticlePricingCondition.PricingConditionType = ArticlePricingCondition.PricingConditionType.FARE,
                                     var subtype : String = "",
                                     var order : Int = 0,
                                     var valueType : ArticlePricingCondition.PricingConditionKind = ArticlePricingCondition.PricingConditionKind.VALUE,
                                     var value : BigDecimal = BigDecimal.ZERO,
                                     var baseValue : BigDecimal = BigDecimal.ZERO,
                                     var conditionValue : BigDecimal = BigDecimal.ZERO,
                                     var totalValue : BigDecimal = BigDecimal.ZERO) {
}