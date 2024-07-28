package org.bastanchu.churiservices.articles.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse

import org.bastanchu.churiservices.articles.internal.service.FormatService
import org.bastanchu.churiservices.core.api.model.GenericResponse
import org.bastanchu.churiservices.core.api.model.article.Format
import org.slf4j.LoggerFactory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*

@RestController
class FormatController(@Autowired val formatService: FormatService,
                       @Autowired val environment: Environment) {

    val logger = LoggerFactory.getLogger(FormatController::class.java)

    @Operation(
        summary = "Creates an article format for articles.",
        requestBody = RequestBody(
            required = true, content = [
                Content(
                    mediaType = "application/json",
                    examples = [ExampleObject("""
                      {
                       "formatId" : "CAN",
                       "description" : "Can of food"
                      }
                    """)]
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201", description = "Format succesfully created", content = [
                    Content(
                        mediaType = "application/json",
                        examples = [ExampleObject("""
                      {
                       "formatId" : "CAN",
                       "description" : "Can of food"
                      }
                    """)]
                    )
                ]
            )
        ]
    )
    @PostMapping("/formats/format")
    @ResponseStatus(HttpStatus.CREATED)
    fun createFormat(@org.springframework.web.bind.annotation.RequestBody format : Format) : Format {
        logger.info("Creating format ${format.formatId}")
        formatService.createFormat(format)
        return format
    }

    @Operation(summary = "Retrieves format data Given its formatId",
               responses = [
                   ApiResponse(
                       responseCode = "200", description = "Response successfully retrieved", content = [
                           Content(
                               mediaType = "application/json",
                               examples = [
                                   ExampleObject("""
                                       {
                                         "formatId": "CAN",
                                         "description": "Can of food"
                                       }
                                   """)
                               ]
                           )
                       ]
                   ),
                   ApiResponse(
                       responseCode = "404", description = "Article format not found", content = [
                           Content(
                               mediaType = "application/json",
                               examples = [
                                   ExampleObject("""
                                       {
                                        "status": "FAILURE",
                                        "description": "Article format CAN not found",
                                        "timestamp": "2024-02-24 19:16:28 +0100"
                                       }
                                   """)
                               ]
                           )
                       ]
                   )
               ])
    @GetMapping("/formats/format/{formatId}")
    @ResponseStatus(HttpStatus.OK)
    fun getFormat(@PathVariable formatId : String) : ResponseEntity<Any> {
        logger.info("Retrieving data for format id ${formatId}")
        val format = formatService.getFormat(formatId)
        if (format != null) {
            return ResponseEntity(format, HttpStatus.OK)
        } else {
            val timestampDateFormat = SimpleDateFormat(environment.getProperty("org.bastanchu.churiservices.timestampFormat"))
            val genericResponse = GenericResponse(status = GenericResponse.Status.FAILURE,
                                                  description = "Article format ${formatId} not found",
                                                  timestamp = timestampDateFormat.format(Date()))
            return ResponseEntity(genericResponse, HttpStatus.NOT_FOUND)
        }
    }

    @Operation(summary = "Retrieves all registered formats",
               responses = [
                   ApiResponse(
                       responseCode = "200", description = "All responses retrived", content = [
                           Content(
                               mediaType = "application/json",
                               examples = [
                                   ExampleObject(
                                       """
                                           [
                                            {
                                             "formatId": "CAN",
                                             "description": "Can of food"
                                            },
                                            {
                                             "formatId": "BOT",
                                             "description": "Bottled item"
                                            }
                                           ]                                           
                                       """
                                   )
                               ]
                           )
                       ]
                   )
               ])
    @GetMapping("/formats")
    fun getFormatsSet() : List<Format> {
        logger.info("Retrieving all formats list")
        val formats = formatService.getFormats()
        return formats
    }
}