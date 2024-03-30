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
    var country : String = "",
    @OneToMany(cascade = arrayOf(CascadeType.ALL),
               orphanRemoval = true)
    @JoinColumn(name = "ARTICLE_ID")
    @OrderBy("formatId ASC")
    var articleFormats : MutableList<ArticleFormatEntity> = ArrayList(),
    @OneToMany(cascade = arrayOf(CascadeType.ALL),
               orphanRemoval = true)
    @JoinColumn(name = "ARTICLE_ID")
    @OrderBy("type ASC, subType ASC")
    var articlePricingConditions : MutableList<PricingConditionEntity> = ArrayList() ) {

    override fun equals(other: Any?): Boolean {
        if (other is ArticleEntity) {
            return ((articleId != null) && (articleId.equals(other.articleId))) || (other.articleId == null)
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        val hash = articleId.hashCode() ?: 0
        return hash
    }
}