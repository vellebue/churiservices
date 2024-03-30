package org.bastanchu.churiservices.articles.internal.service

import org.bastanchu.churiservices.core.api.model.article.Article

interface ArticleService {

    fun createArticle(article : Article) : Article

    fun getArticle(articleId : String) : Article?

    fun getArticles() : List<Article>
}