package org.bastanchu.churiservices.articles.internal.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "PRICING_CONDITIONS")
data class PricingConditionEntity(
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_PRICING_CONDITIONS")
    @SequenceGenerator(name = "SEQ_PRICING_CONDITIONS", sequenceName = "SEQ_PRICING_CONDITIONS", allocationSize = 1)
    var id : Int? = null,
    @Column(name = "ARTICLE_ID")
    var articleId : String? = null,
    @Column(name = "TYPE")
    var type : String? = null,
    @Column(name = "SUBTYPE")
    var subtype : String? = null,
    @Column(name = "NUM_ORDER")
    var order : Int = 0,
    @Column(name = "VALUE_TYPE")
    var valueType : String = "",
    @Column(name = "VALUE")
    var value : BigDecimal = BigDecimal(0.0)
) {

    override fun equals(other: Any?): Boolean {
        if (other is PricingConditionEntity) {
            return (articleId == other.articleId) && (type == other.type) && (subtype == other.subtype)
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var hash = articleId.hashCode() ?: 0
        hash = 37 * hash + (type.hashCode() ?: 0)
        hash = 37 * hash + (subtype.hashCode() ?: 0)
        return hash
    }
}