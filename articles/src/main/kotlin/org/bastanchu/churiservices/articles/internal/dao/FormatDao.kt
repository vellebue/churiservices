package org.bastanchu.churiservices.articles.internal.dao

import org.bastanchu.churiservices.articles.internal.entity.FormatEntity
import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.model.article.Format

interface FormatDao : BaseValueObjectDao<String, FormatEntity, Format> {
}