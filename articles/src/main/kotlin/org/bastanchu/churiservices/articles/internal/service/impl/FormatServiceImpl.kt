package org.bastanchu.churiservices.articles.internal.service.impl

import org.bastanchu.churiservices.articles.internal.dao.FormatDao
import org.bastanchu.churiservices.articles.internal.service.FormatService
import org.bastanchu.churiservices.core.api.model.article.Format
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class FormatServiceImpl(@Autowired val formatDao : FormatDao) : FormatService {
    override fun createFormat(format: Format) {
        val formatEntity = formatDao.fromValueObjectToEntity(format)
        formatDao.create(formatEntity)
    }

    override fun getFormat(formatId: String) : Format? {
        val formatEntity = formatDao.getById(formatId)
        if (formatEntity != null) {
            return formatDao.toValueObject(formatEntity)
        } else {
            return null
        }
    }

    override fun getFormats(): List<Format> {
        val formatEntities = formatDao.listAll()
        return formatDao.toValueObjectList(formatEntities)
    }
}