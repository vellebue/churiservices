package org.bastanchu.churiservices.core.api.model.article

import io.swagger.v3.oas.annotations.media.Schema
import org.bastanchu.churiservices.core.api.model.addresses.Country

@Schema(name = "Article",
        description = "Article reference which describes product features.")
data class Article(@field:Schema(description = "Article ID to identify some kind of article.",
                                 example = "COMP00021",
                                 required = true)
                   var articleId : String = "",
                   @field:Schema(description = "Article description.",
                                 example = "Compat Laptop RX3600 17''",
                                 required = true)
                   var description : String = "",
                   @field:Schema(description = "Country ISO code that points article origin.",
                                 example = "US",
                                 required = true)
                   var country: String = "",
                   @field:Schema(description = "Article pricing conditions to give value for this article.",
                                 required = true)
                   var articleConditions : List<ArticlePricingCondition> = ArrayList(),
                   @field:Schema(description = "Article formats defined for this article.")
                   var articleFormats : List<ArticleFormat> = ArrayList()) {
}