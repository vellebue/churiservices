package org.bastanchu.churiservices.core.api.model.orders

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(name = "OrderLine",
        description = "Data structure to describe order lines that include items required.")
data class OrderLine(@field:Schema(description = "Article Id. to identify required item",
                                       example = "ORTE37553" ,
                                      required = true)
                     var articleId : String = "",
                     @field:Schema(description = "Returning value for article description.",
                                       example = "Compaq Laptop Multimedia Series 17\"." ,
                                      required = false)
                     var articleDescription : String = "",
                     @field:Schema(description = "Number of units asked for this order item.",
                                       example = "3" ,
                                      required = true)
                     var numItems : Int = 0,
                     @field:Schema(description = "Format measure unit for this article line",
                         example = "UN" ,
                         required = true)
                     var formatUnit : String = "",
                     @field:Schema(description = "Base price for a unit of this article.",
                                       example = "1200.70" ,
                                      required = true)
                     var basePrice : BigDecimal = BigDecimal(0),
                     @field:Schema(description = "VAT tax value for a unit of this article.",
                                       example = "120.07" ,
                                      required = true)
                     var vatTax : BigDecimal = BigDecimal(0),
                     @field:Schema(description = "Total price for a unit in this article.",
                                       example = "1320.77" ,
                                      required = true)
                     var totalPrice : BigDecimal = BigDecimal(0),
                     @field:Schema(description = "Base value for all units for this article.",
                         example = "2401.40" ,
                         required = true)
                     var baseValue : BigDecimal = BigDecimal(0),
                     @field:Schema(description = "VAT tax value for all units for this article.",
                         example = "240.14" ,
                         required = true)
                     var vatTaxValue : BigDecimal = BigDecimal(0),
                     @field:Schema(description = "Total gross value for this line.",
                                       example = "3960.22" ,
                                      required = true)
                     var totalValue : BigDecimal = BigDecimal(0),
                     @field:Schema(description = "Order line pricing conditions scale",
                                   required = true)
                     var conditions : List<OrderLinePricingCondition> = ArrayList()) {
}