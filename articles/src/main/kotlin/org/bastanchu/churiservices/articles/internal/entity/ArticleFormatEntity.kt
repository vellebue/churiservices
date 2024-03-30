package org.bastanchu.churiservices.articles.internal.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "ARTICLE_FORMATS")
data class ArticleFormatEntity(
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "SEQ_ARTICLE_FORMATS")
    @SequenceGenerator(name = "SEQ_ARTICLE_FORMATS", sequenceName = "SEQ_ARTICLE_FORMATS", allocationSize = 1)
    var id : Int? = null,
    @Column(name = "ARTICLE_ID")
    var articleId : String? = null,
    @Column(name = "FORMAT_ID")
    var formatId : String? = null,
    @Column(name = "DESCRIPTION")
    var description : String = "",
    @Column(name = "EAN_CODE")
    var eanCode : String = "",
    @Column(name = "EAN_TYPE")
    var eanType : String = "",
    @Column(name = "MIN_UNIT")
    var minUnit: Boolean = true,
    @Column(name = "SALE_UNIT")
    var saleUnit : Boolean = true,
    @Column(name ="CONVERSION_FACTOR")
    var conversionFactor : BigDecimal = BigDecimal(1.0)
) {
    override fun equals(other: Any?): Boolean {
        if (other is ArticleFormatEntity) {
            return (articleId == other.articleId) && (formatId == other.formatId)
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var hash = articleId.hashCode() ?: 0
        hash = 37 * hash + (formatId.hashCode() ?: 0)
        return hash
    }
}