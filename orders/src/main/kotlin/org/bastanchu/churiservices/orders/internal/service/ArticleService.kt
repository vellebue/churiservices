package org.bastanchu.churiservices.orders.internal.service

import org.bastanchu.churiservices.core.api.model.article.Article

interface ArticleService {

    fun getArticle(articleId : String) : Article?
}