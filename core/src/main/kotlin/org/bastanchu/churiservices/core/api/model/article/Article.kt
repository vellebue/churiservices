package org.bastanchu.churiservices.core.api.model.article

import io.swagger.v3.oas.annotations.media.Schema
import org.bastanchu.churiservices.core.api.model.addresses.Country

@Schema(name = "Article",
        description = "Article reference which describes product features.")
data class Article(@field:Schema(description = "Article ID to identify some kind of article.",
                                 example = "COMP00021",
                                 required = true)
                   val articleId : String,
                   @field:Schema(description = "Article description.",
                                 example = "Compat Laptop RX3600 17''",
                                 required = true)
                   val description : String,
                   @field:Schema(description = "Country ISO code that points article origin.",
                                 example = "US",
                                 required = true)
                   val country: String,
                   @field:Schema(description = "Article pricing conditions to give value for this article.",
                                 required = true)
                   val articleConditions : List<ArticlePricingCondition> = ArrayList<ArticlePricingCondition>(),
                   @field:Schema(description = "Article formats defined for this article.")
                   val articleFormats : List<ArticleFormat> = ArrayList<ArticleFormat>()) {
}