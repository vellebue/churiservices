package org.bastanchu.churiservices.articles.internal.entity

import jakarta.persistence.*

@Entity
@Table(name = "ARTICLE_FORMATS")
@IdClass(ArticleFormatPK::class)
data class ArticleFormatEntity(
    @Id
    @Column(name = "ARTICLE_ID")
    var articleId : String? = null,
    @Id
    @Column(name = "FORMAT_ID")
    var formatId : String? = null,
    @Column(name = "MIN_UNIT")
    var minUnit: Boolean = true,
    @Column(name = "SALE_UNIT")
    var saleUnit : Boolean = true,
    @Column(name ="CONVERSION_FACTOR")
    var conversionFactor : Double = 1.0
) {
}