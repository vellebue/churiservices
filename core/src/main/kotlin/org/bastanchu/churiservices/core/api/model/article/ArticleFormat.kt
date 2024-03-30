package org.bastanchu.churiservices.core.api.model.article

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal

@Schema(name = "ArticleFormat",
        description = "Each article format defined to sell a certain article reference.")
data class ArticleFormat(@field:Schema(description = "Format ID for this format",
                                       example = "BOT",
                                       required = true)
                         var formatId : String = "",
                         @field:Schema(description = "Format description in the context of this article.",
                                       example = "Bottle of Campani Rose 0.70 L.",
                                       required = true)
                         var description : String = "",
                         @field:Schema(description = "EAN Code for this article format.",
                                       example = "72316253347",
                                       required = false)
                         var eanCode : String? = null,
                         @field:Schema(description = "EAN Code type.",
                                       example = "EAN11",
                                       required = false)
                         var eanType : String? = null,
                         @field:Schema(description = "True if this is the minimum format for reference, false otherwise.",
                                       example = "true",
                                       required = true)
                         var minUnit : Boolean = false,
                         @field:Schema(description = "True if this format is available for sale, false otherwise.",
                             example = "true",
                             required = true)
                         var saleUnit : Boolean = true,
                         @field:Schema(description = "Conversion factor for this format (number of minimum format units contained for this format in the context of this article).",
                                       example = "1.0",
                                       required = true)
                         var conversionFactor : BigDecimal = BigDecimal(1.0)) {
}