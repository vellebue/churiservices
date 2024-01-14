package org.bastanchu.churiservices.core.api.model

import io.swagger.v3.oas.annotations.media.Schema
@Schema(name = "GenericResponse",
    description = "Generic response data type to notify generic result status.")
data class GenericResponse(
    @field:Schema(description = "Response status for sending call", type = "GenericResponse.Status", example = "SUCCESS", required = true)
    val status : Status,
    @field:Schema(description = "Generic description for this response.", example = "Valid item 35143 registered" , required = true)
    val description : String,
    @field:Schema(description = "Timestamp assigned by server",
        example = "2023-12-07 18:05:09 +0100",
        required = true)
    val timestamp : String
) {
    enum class Status(val status : String) {
        SUCESS("success"), FAILURE("FAILURE")
    }
}