package org.bastanchu.churiservices.articles.internal.service

import org.bastanchu.churiservices.core.api.model.article.Format

interface FormatService {

    fun createFormat(format : Format)

    fun getFormat(formatId : String) : Format?

    fun getFormats() : List<Format>

}