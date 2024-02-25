package org.bastanchu.churiservices.articles.internal.entity

import java.io.Serializable

data class ArticleFormatPK(var articleId : String? = null,
                           var formatId : String? = null) : Serializable {
}