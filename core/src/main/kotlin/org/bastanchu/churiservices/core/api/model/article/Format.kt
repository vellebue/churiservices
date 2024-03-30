package org.bastanchu.churiservices.core.api.model.article

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Format",
        description = "Packaging format for articles.")
data class Format(
                  @field:Schema(description = "Format Id unique identificator.",
                                example = "UN",
                                required = true)
                  var formatId : String = "",
                  @field:Schema(description = "Format Id description",
                                example = "Single unit format.",
                                required = true)
                  var description : String = "") {
}