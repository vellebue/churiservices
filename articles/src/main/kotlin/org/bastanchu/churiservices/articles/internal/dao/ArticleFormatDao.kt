package org.bastanchu.churiservices.articles.internal.dao

import org.bastanchu.churiservices.articles.internal.entity.ArticleFormatEntity
import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.article.ArticleFormat

interface ArticleFormatDao : BaseValueObjectDao<Int, ArticleFormatEntity, ArticleFormat> {
}