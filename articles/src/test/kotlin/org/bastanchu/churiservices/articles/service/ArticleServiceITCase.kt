package org.bastanchu.churiservices.articles.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.bastanchu.churiservices.articles.internal.service.ArticleService
import org.bastanchu.churiservices.core.api.model.article.Article
import org.bastanchu.churiservices.core.api.model.article.ArticleFormat
import org.bastanchu.churiservices.core.api.model.article.ArticlePricingCondition
import org.bastanchu.churiservices.core.api.service.exception.ServiceException
import org.bastanchu.churiservices.coretest.api.test.BaseITCase

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

import org.springframework.beans.factory.annotation.Autowired
import java.math.BigDecimal
import javax.sql.DataSource

class ArticleServiceITCase(@Autowired val articleService: ArticleService,
                           @Autowired val objectMapper : ObjectMapper) :BaseITCase() {

    override fun getScriptContent(): String {
        return """
            --Preparation: Create Formats for articles
            INSERT INTO public.formats
            (format_id, description)
            VALUES('CS', 'Box of several units.');
            INSERT INTO public.formats
            (format_id, description)
            VALUES('UN', 'Single item reference unit.');
            --Create Article Entry
            INSERT INTO public.articles
            (article_id, description, country_id)
            VALUES('COMPQ0017', 'Compq computer 17''''', 'ES');
            --Create Article Format entries
            INSERT INTO public.article_formats
            (id, article_id, format_id, description, ean_code, ean_type, min_unit, sale_unit, conversion_factor)
            VALUES(0, 'COMPQ0017', 'UN', 'Compq computer single laptop 17''''', '87613456194', 'EAN11', true, true, 1.000);
            INSERT INTO public.article_formats
            (id, article_id, format_id, description, ean_code, ean_type, min_unit, sale_unit, conversion_factor)
            VALUES(1, 'COMPQ0017', 'CS', 'Retail Box of Compq computer single laptop 17''''', '1197562765241', 'EAN13', false, true, 6.000);
            --Roll article format sequence to balance
            select nextval('seq_article_formats');
            select nextval('seq_article_formats');
            --Create pricing condition entries
            INSERT INTO public.pricing_conditions
            (id, article_id, "type", subtype, num_order, value_type, value)
            VALUES(0, 'COMPQ0017', 'FARE', '', 1, 'VALUE', 1200.0000);
            INSERT INTO public.pricing_conditions
            (id, article_id, "type", subtype, num_order, value_type, value)
            VALUES(1, 'COMPQ0017', 'VAT_TAX', 'NR', 90, 'PERCENTAGE', 21.0000);
            --Roll pricing conditions sequence to balance
            select nextval('seq_pricing_conditions');
            select nextval('seq_pricing_conditions');
            COMMIT;
        """.trimIndent()
    }

    @Test
    fun `should get full article data by article id properly`() {
        val articleId = "COMPQ0017"
        val article = articleService.getArticle(articleId)
        assertNotNull(article)
        // article heading asserts
        assertEquals("COMPQ0017", article?.articleId)
        assertEquals("Compq computer 17''", article?.description)
        assertEquals("ES", article?.country)
        // article formats
        val articleFormatCSBox = article?.articleFormats?.get(0)!!
        assertEquals("CS", articleFormatCSBox.formatId)
        assertEquals("Retail Box of Compq computer single laptop 17''", articleFormatCSBox.description)
        assertEquals("1197562765241", articleFormatCSBox.eanCode)
        assertEquals("EAN13", articleFormatCSBox.eanType)
        assertEquals(false, articleFormatCSBox.minUnit)
        assertEquals(true, articleFormatCSBox.saleUnit)
        assertEquals(BigDecimal(6.0), articleFormatCSBox.conversionFactor.setScale(0))
        val articleFormatUNUnit = article?.articleFormats?.get(1)!!
        assertEquals("UN", articleFormatUNUnit.formatId)
        assertEquals("Compq computer single laptop 17''", articleFormatUNUnit.description)
        assertEquals("87613456194", articleFormatUNUnit.eanCode)
        assertEquals("EAN11", articleFormatUNUnit.eanType)
        assertEquals(true, articleFormatUNUnit.minUnit)
        assertEquals(true, articleFormatUNUnit.saleUnit)
        assertEquals(BigDecimal(1.0), articleFormatUNUnit.conversionFactor.setScale(0))
        // Pricing conditions
        val pricingConditionFare = article.articleConditions?.get(0)!!
        assertEquals(ArticlePricingCondition.PricingConditionType.FARE, pricingConditionFare.type)
        assertEquals("", pricingConditionFare.subtype)
        assertEquals(1, pricingConditionFare.order)
        assertEquals(ArticlePricingCondition.PricingConditionKind.VALUE, pricingConditionFare.valueType)
        assertEquals(BigDecimal(1200), pricingConditionFare.value.setScale(0))
        val pricingConditionTax = article.articleConditions?.get(1)!!
        assertEquals(ArticlePricingCondition.PricingConditionType.VAT_TAX, pricingConditionTax.type)
        assertEquals("NR", pricingConditionTax.subtype)
        assertEquals(90, pricingConditionTax.order)
        assertEquals(ArticlePricingCondition.PricingConditionKind.PERCENTAGE, pricingConditionTax.valueType)
        assertEquals(BigDecimal(21.0), pricingConditionTax.value.setScale(0))
    }

    @Test
    fun `should retrieve nothing when non registered article id is given`() {
        val articleId = "nonExistent"
        val article = articleService.getArticle(articleId)
        assertNull(article)
    }

    @Test
    fun `should fail when creating repeated article`() {
        val classLoader = Thread.currentThread().contextClassLoader
        val stream = classLoader.getResourceAsStream("org/bastanchu/churiservices/articles/service/createExistingArticleData.json")
        stream.use {
            val article = objectMapper.readValue(it, Article::class.java)
            assertThrows(ServiceException::class.java) {
                articleService.createArticle(article)
            }
        }
    }

    @Test
    fun `should create an article properly`() {
        val classLoader = Thread.currentThread().contextClassLoader
        val stream = classLoader.getResourceAsStream("org/bastanchu/churiservices/articles/service/createArticleData.json")
        stream.use {
            val article = objectMapper.readValue(it, Article::class.java)
            articleService.createArticle(article)
            // Test article heading
            val dbArticle = retrieveArticleHeadingData(article.articleId)
            assertEquals(article.articleId, dbArticle.articleId)
            assertEquals(article.description, dbArticle.description)
            assertEquals(article.country, dbArticle.country)
            // Test article formats
            val dbArticleformats = retrieveArticleFormatsData(article.articleId)
            for (i in 0..(dbArticleformats.size - 1)) {
                val dbArticleFormat = dbArticleformats[i]
                val articleFormat = article.articleFormats[i]
                assertEquals(articleFormat.formatId, dbArticleFormat.formatId)
                assertEquals(articleFormat.description, dbArticleFormat.description)
                assertEquals(articleFormat.eanCode, dbArticleFormat.eanCode)
                assertEquals(articleFormat.eanType, dbArticleFormat.eanType)
                assertEquals(articleFormat.minUnit, dbArticleFormat.minUnit)
                assertEquals(articleFormat.saleUnit, dbArticleFormat.saleUnit)
            }
            // Test article pricing conditions
            val dbPricingConditions = retrieveArticlePricingConditions(article.articleId)
            for (i in 0..(dbArticleformats.size - 1)) {
                val dbPricingCondition = dbPricingConditions[i]
                val pricingCondition = article.articleConditions[i]
                assertEquals(pricingCondition.type, dbPricingCondition.type)
                assertEquals(pricingCondition.subtype,dbPricingCondition.subtype)
                assertEquals(pricingCondition.order, dbPricingCondition.order)
                assertEquals(pricingCondition.valueType, dbPricingCondition.valueType)
                assertEquals(pricingCondition.value.setScale(3), dbPricingCondition.value.setScale(3))
            }
        }
    }

    fun retrieveArticleHeadingData(articleId : String) : Article {
        val sql = "select article_id, description, country_id from ARTICLES where article_id = ? "
        val conn = dataSource?.connection!!
        val article = Article()
        conn.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, articleId)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    if (it.next()) {
                        article.articleId = it.getString("article_id")
                        article.description = it.getString("description")
                        article.country = it.getString("country_id")
                    }
                }
            }
        }
        return article
    }

    fun retrieveArticleFormatsData(articleId : String) : List<ArticleFormat> {
        val sql = "select id, article_id, format_id, description, ean_code, ean_type, min_unit, sale_unit " +
                  " from ARTICLE_FORMATS where article_id = ? ORDER BY id "
        val articleFormats = ArrayList<ArticleFormat>()
        val conn = dataSource?.connection!!
        conn.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, articleId)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    while (it.next()) {
                        val articleFormat = ArticleFormat()
                        articleFormat.formatId = it.getString("format_id")
                        articleFormat.description = it.getString("description")
                        articleFormat.eanCode = it.getString("ean_code")
                        articleFormat.eanType = it.getString("ean_type")
                        articleFormat.minUnit = it.getBoolean("min_unit")
                        articleFormat.saleUnit = it.getBoolean("sale_unit")
                        articleFormats.add(articleFormat)
                    }
                }
            }
        }
        return articleFormats
    }

    fun retrieveArticlePricingConditions(articleId : String) : List<ArticlePricingCondition> {
        val sql = "select type, subtype, num_order, value_type, value " +
                  " from PRICING_CONDITIONS where article_id = ? order by type, subtype "
        val articlePricingConditions = ArrayList<ArticlePricingCondition>()
        val conn = dataSource?.connection!!
        conn.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, articleId)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    while (it.next()) {
                        val pricingCondition = ArticlePricingCondition()
                        pricingCondition.type = ArticlePricingCondition.PricingConditionType.valueOf(it.getString("type"))
                        pricingCondition.subtype = it.getString("subtype")
                        pricingCondition.order = it.getInt("num_order")
                        pricingCondition.valueType = ArticlePricingCondition.PricingConditionKind.valueOf(it.getString("value_type"))
                        pricingCondition.value = it.getBigDecimal("value")
                        articlePricingConditions.add(pricingCondition)
                    }
                }
            }
        }
        return articlePricingConditions
    }
}