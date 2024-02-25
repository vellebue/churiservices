package org.bastanchu.churiservices.core.api.model.article

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ArticleFormat",
        description = "Each article format defined to sell a certain article reference.")
data class ArticleFormat(@field:Schema(description = "Format ID for this format",
                                       example = "BOT",
                                       required = true)
                         val formatId : String,
                         @field:Schema(description = "Format description in the context of this article.",
                                       example = "Bottle of Campani Rose 0.70 L.",
                                       required = true)
                         val description : String,
                         @field:Schema(description = "True if this is the minimum format for reference, false otherwise.",
                                       example = "true",
                                       required = true)
                         val minUnit : Boolean,
                         @field:Schema(description = "True if this format is available for sale, false otherwise.",
                             example = "true",
                             required = true)
                         val saleUnit : Boolean,
                         @field:Schema(description = "Conversion factor for this format (number of minimum format units contained for this format in the context of this article).",
                                       example = "1.0",
                                       required = true)
                         val conversionFactor : Double) {
}