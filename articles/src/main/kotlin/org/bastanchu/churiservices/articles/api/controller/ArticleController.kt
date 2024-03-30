package org.bastanchu.churiservices.articles.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.bastanchu.churiservices.articles.internal.service.ArticleService
import org.bastanchu.churiservices.core.api.model.article.Article
import org.bastanchu.churiservices.core.api.service.exception.ItemNotFoundException

import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class ArticleController(@Autowired val articleService: ArticleService) {

    val logger = LoggerFactory.getLogger(ArticleController::class.java)

    @Operation(
        summary = "Creates a new article",
        requestBody = RequestBody(
            required = true, content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            """
                                {
                                    "articleId" : "COMPQ0017",
                                    "description" : "Compq computer 17''",
                                    "country" : "ES",
                                    "articleConditions" : [
                                        {
                                            "type": "FARE",
                                            "order" : 1,
                                            "valueType" : "VALUE",
                                            "value" : 1200.0
                                        },
                                        {
                                            "type": "VAT_TAX",
                                            "subtype" : "NR",
                                            "order" : 90,
                                            "valueType" : "PERCENTAGE",
                                            "value" : 21.0
                                        }
                                    ],
                                    "articleFormats" : [
                                        {
                                            "formatId" : "UN",
                                            "description" : "Compq computer single laptop 17''",
                                            "eanCode" : "87613456194",
                                            "eanType" : "EAN11",
                                            "minUnit" : true,
                                            "saleUnit" : true,
                                            "conversionFactor" : 1.0
                                        },
                                        {
                                            "formatId" : "CS",
                                            "description" : "Retail Box of Compq computer single laptop 17''",
                                            "eanCode" : "1197562765241",
                                            "eanType" : "EAN13",
                                            "minUnit" : false,
                                            "saleUnit" : true,
                                            "conversionFactor" : 6.0
                                        }
                                    ]
                                }
                            """
                        )
                    ]
                )
            ]
        )
    )
    @ApiResponses(
        value = arrayOf(
            ApiResponse(
                responseCode = "201", description = "Article created as described", content = [
                    Content(
                        mediaType = "application/json",
                        examples = [ExampleObject(
                            """
                            {
                                "articleId" : "COMPQ0017",
                                "description" : "Compq computer 17''",
                                "country" : "ES",
                                "articleConditions" : [
                                    {
                                        "type": "FARE",
                                        "order" : 1,
                                        "valueType" : "VALUE",
                                        "value" : 1200.0
                                    },
                                    {
                                        "type": "VAT_TAX",
                                        "subtype" : "NR",
                                        "order" : 90,
                                        "valueType" : "PERCENTAGE",
                                        "value" : 21.0
                                    }
                                ],
                                "articleFormats" : [
                                    {
                                        "formatId" : "UN",
                                        "description" : "Compq computer single laptop 17''",
                                        "eanCode" : "87613456194",
                                        "eanType" : "EAN11",
                                        "minUnit" : true,
                                        "saleUnit" : true,
                                        "conversionFactor" : 1.0
                                    },
                                    {
                                        "formatId" : "CS",
                                        "description" : "Retail Box of Compq computer single laptop 17''",
                                        "eanCode" : "1197562765241",
                                        "eanType" : "EAN13",
                                        "minUnit" : false,
                                        "saleUnit" : true,
                                        "conversionFactor" : 6.0
                                    }
                                ]
                            }
                        """
                        )]
                    )
                ]
            ), ApiResponse(
                responseCode = "400", description = "Error creating article", content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                """
                                  {
                                    "type": "/articlesService",
                                    "title": "Service Exception",
                                    "status": 400,
                                    "detail": "Article with id COMPQ0017 already exists.",
                                    "instance": "/articles/article",
                                    "timestamp": "2024-03-29T20:33:52.104094481Z"
                                  }
                              """
                            )
                        ]
                    )
                ]
            )
        )
    )
    @PostMapping("/articles/article")
    @ResponseStatus(HttpStatus.CREATED)
    fun createArticle(@org.springframework.web.bind.annotation.RequestBody article: Article): ResponseEntity<Article> {
        logger.info("Registerring article with articleId: ${article.articleId}")
        val article = articleService.createArticle(article)
        val responseEntity = ResponseEntity(article, HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
        return responseEntity
    }

    @Operation(summary = "Retrieves an article full description corresponding to given\"articleId\"")
    @ApiResponses(value = arrayOf(
         ApiResponse(responseCode = "200", description = "Article successfully retrieved", content = [
             Content(mediaType = "application/json",
                     examples = [
                         ExampleObject(
                             """
                                 {
                                    "articleId": "COMPQ0017",
                                    "description": "Compq computer 17''",
                                    "country": "ES",
                                    "articleConditions": [
                                        {
                                            "type": "FARE",
                                            "subtype": "",
                                            "order": 1,
                                            "valueType": "VALUE",
                                            "value": 1200.0000
                                        },
                                        {
                                            "type": "VAT_TAX",
                                            "subtype": "NR",
                                            "order": 90,
                                            "valueType": "PERCENTAGE",
                                            "value": 21.0000
                                        }
                                    ],
                                    "articleFormats": [
                                        {
                                            "formatId": "CS",
                                            "description": "Retail Box of Compq computer single laptop 17''",
                                            "eanCode": "1197562765241",
                                            "eanType": "EAN13",
                                            "minUnit": false,
                                            "saleUnit": true,
                                            "conversionFactor": 6.000
                                        },
                                        {
                                            "formatId": "UN",
                                            "description": "Compq computer single laptop 17''",
                                            "eanCode": "87613456194",
                                            "eanType": "EAN11",
                                            "minUnit": true,
                                            "saleUnit": true,
                                            "conversionFactor": 1.000
                                        }
                                    ]
                                }
                             """
                         )
                     ])
         ]),
         ApiResponse(
             responseCode = "404", description = "Article not found", content = [
                 Content(mediaType = "application/json",
                         examples = [
                             ExampleObject(
                                 """
                                     {
                                        "type": "/articlesService",
                                        "title": "Item Not Found Exception",
                                        "status": 404,
                                        "detail": "Article not found COMPQ017",
                                        "instance": "/articles/article/COMPQ017",
                                        "timestamp": "2024-03-29T21:05:52.687362411Z"
                                    }
                                 """
                             )
                         ])
             ]
         )
        )
    )
    @GetMapping("/articles/article/{articleId}")
    @ResponseStatus(HttpStatus.OK)
    fun getArticle(@PathVariable articleId : String) : Article {
        logger.info("Getting article ${articleId}")
        val article = articleService.getArticle(articleId)
        if (article != null) {
            return article
        } else {
            throw ItemNotFoundException("Article not found ${articleId}")
        }
    }

    @Operation(summary = "Retrieves all articles.")
    @ApiResponses(value = arrayOf(
            ApiResponse(responseCode = "200", description = "List of all articles.", content = [
                Content(mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                """
                                    [
                                        {
                                            "articleId": "COMPQ0017",
                                            "description": "Compq computer 17''",
                                            "country": "ES",
                                            "articleConditions": [],
                                            "articleFormats": []
                                        }
                                    ]
                                """
                            )
                        ])
            ])
        )
    )
    @GetMapping("/articles")
    @ResponseStatus(HttpStatus.OK)
    fun getArticles() : List<Article> {
        logger.info("Getting all articles")
        val articles = articleService.getArticles()
        return articles
    }
}