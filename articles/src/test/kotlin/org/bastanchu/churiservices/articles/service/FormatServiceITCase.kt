package org.bastanchu.churiservices.articles.service

import org.bastanchu.churiservices.articles.internal.service.FormatService
import org.bastanchu.churiservices.core.api.model.article.Format
import org.bastanchu.churiservices.coretest.api.test.BaseITCase

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired

class FormatServiceITCase(@Autowired val formatService: FormatService) :BaseITCase() {

    override fun getScriptContent(): String {
        return """
            insert into FORMATS(FORMAT_ID, DESCRIPTION)
            values('UN', 'Default Unit Format');
            commit;
        """.trimIndent()
    }

    @Test
    fun `should create an article format property`() {
        val format = Format("CAN", "Can of food format")
        formatService.createFormat(format)
        val dbFormat = retrieveFormat(format.formatId)
        assertNotNull(dbFormat)
        assertEquals(format.formatId, dbFormat?.formatId)
        assertEquals(format.description, dbFormat?.description)
    }

    @Test
    fun `should retrieve an article format propertly`() {
        val formatId = "UN"
        val format = formatService.getFormat(formatId)
        assertEquals("UN", format?.formatId)
        assertEquals("Default Unit Format", format?.description)
    }

    @Test
    fun `should not retrieve an article format when formatId does not exists`() {
        val formatId = "NOEXIST"
        val format = formatService.getFormat(formatId)
        assertNull(format)
    }

    private fun retrieveFormat(formatId : String) : Format? {
        val query = "select FORMAT_ID, DESCRIPTION from FORMATS where FORMAT_ID = ?"
        var format : Format? = null
        val con = dataSource!!.connection
        con.use {
            val statement = it.prepareStatement(query)
            statement.setString(1, formatId)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    if (it.next()) {
                       format = Format(
                           it.getString("FORMAT_ID"),
                           it.getString("DESCRIPTION")
                       )
                    }
                }
            }
        }
        return format
    }
}