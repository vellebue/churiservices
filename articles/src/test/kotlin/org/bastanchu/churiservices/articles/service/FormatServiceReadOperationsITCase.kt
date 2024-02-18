package org.bastanchu.churiservices.articles.service

import org.bastanchu.churiservices.articles.internal.service.FormatService
import org.bastanchu.churiservices.coretest.api.test.BaseITCase
import org.springframework.beans.factory.annotation.Autowired

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class FormatServiceReadOperationsITCase(@Autowired val formatService : FormatService) : BaseITCase() {

    override fun getScriptContent(): String {
        return """
            insert into FORMATS(FORMAT_ID, DESCRIPTION)
            values('UN', 'Default Unit Format');
            insert into FORMATS(FORMAT_ID, DESCRIPTION)
            values('CAN', 'Can Of Food');
            commit;
        """.trimIndent()
    }

    @Test
    fun `should retrieve all formats properly`() {
        val allFormats = formatService.getFormats()
        val formatsMap = allFormats.groupBy { it.formatId }
        val formatUN = formatsMap["UN"]?.get(0)
        val formatCAN = formatsMap["CAN"]?.get(0)
        assertEquals("UN", formatUN?.formatId)
        assertEquals("Default Unit Format", formatUN?.description)
        assertEquals("CAN", formatCAN?.formatId)
        assertEquals("Can Of Food", formatCAN?.description)
    }
}