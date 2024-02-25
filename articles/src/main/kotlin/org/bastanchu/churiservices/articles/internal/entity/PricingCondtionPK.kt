package org.bastanchu.churiservices.articles.internal.entity

import java.io.Serializable

data class PricingCondtionPK(
    var articleId : String? = null,
    var type : String? = null,
    var subType : String? = null
) : Serializable {
}