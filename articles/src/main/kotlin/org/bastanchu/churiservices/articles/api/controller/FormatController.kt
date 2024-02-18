package org.bastanchu.churiservices.articles.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse

import org.bastanchu.churiservices.articles.internal.service.FormatService
import org.bastanchu.churiservices.core.api.model.article.Format

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class FormatController(@Autowired val formatService: FormatService) {

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
        formatService.createFormat(format)
        return format
    }
}