package org.bastanchu.churiservices.articles.internal.entity

import jakarta.persistence.*
@Entity
@Table(name = "PRICING_CONDITIONS")
@IdClass(PricingCondtionPK::class)
data class PricingConditionEntity(
    @Id
    @Column(name = "ARTICLE_ID")
    var articleId : String? = null,
    @Id
    @Column(name = "TYPE")
    var type : String? = null,
    @Id
    @Column(name = "SUBTYPE")
    var subType : String? = null,
    @Column(name = "NUM_ORDER")
    var numOrder : Int = 0,
    @Column(name = "VALUE_TYPE")
    var valueType : String = "",
    @Column(name = "VALUE")
    var value : Double = 0.0
) {
}