package org.bastanchu.churiservices.articles.service

import org.bastanchu.churiservices.articles.internal.service.ArticleService
import org.bastanchu.churiservices.coretest.api.test.BaseITCase

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired

class ArticleServiceReadOperationsITCase(@Autowired val articleService: ArticleService) : BaseITCase() {
    override fun getScriptContent(): String {
        return """
             --Create Article Entry
            INSERT INTO public.articles
            (article_id, description, country_id)
            VALUES('COMPQ0017', 'Compq computer 17''''', 'ES');
             --Create Article Entry
            INSERT INTO public.articles
            (article_id, description, country_id)
            VALUES('COMPQ0018', 'Compq computer 18''''', 'US');
            COMMIT;
        """.trimIndent()
    }

    @Test
    fun `should get all articles properly`() {
        val allArticles = articleService.getArticles()
        assertEquals(2, allArticles.size)
        val articlesMap = allArticles.groupBy { it.articleId }
        val article1 = articlesMap["COMPQ0017"]?.get(0)!!
        assertEquals("COMPQ0017",article1.articleId)
        assertEquals("Compq computer 17''", article1.description)
        assertEquals("ES", article1.country)
        val article2 = articlesMap["COMPQ0018"]?.get(0)!!
        assertEquals("COMPQ0018",article2.articleId)
        assertEquals("Compq computer 18''", article2.description)
        assertEquals("US", article2.country)
    }
}