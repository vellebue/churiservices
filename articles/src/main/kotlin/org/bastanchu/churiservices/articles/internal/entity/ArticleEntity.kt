package org.bastanchu.churiservices.articles.internal.entity

import jakarta.persistence.*

@Entity
@Table(name = "ARTICLES")
data class ArticleEntity(
    @Id
    @Column(name = "ARTICLE_ID")
    var articleId : String? = null,
    @Column(name = "DESCRIPTION")
    var description: String = "",
    @Column(name = "COUNTRY_ID")
    var countryId : String = "",
    @OneToMany(cascade = arrayOf(CascadeType.ALL),
               orphanRemoval = true)
    @JoinColumn(name = "ARTICLE_ID")
    @OrderColumn(name = "FORMAT_ID")
    var articleFormats : MutableList<ArticleFormatEntity> = ArrayList(),
    @OneToMany(cascade = arrayOf(CascadeType.ALL),
               orphanRemoval = true)
    @JoinColumn(name = "ARTICLE_ID")
    @OrderBy("type ASC, subType ASC")
    var articlePricingConditions : MutableList<PricingConditionEntity> = ArrayList() ) {
}