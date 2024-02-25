package org.bastanchu.churiservices.articles.internal.dao

import org.bastanchu.churiservices.articles.internal.entity.ArticleEntity
import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.article.Article

interface ArticleDao : BaseValueObjectDao<String, ArticleEntity, Article> {
}