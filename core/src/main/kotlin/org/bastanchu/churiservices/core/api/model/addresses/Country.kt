package org.bastanchu.churiservices.core.api.model.addresses

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Country",
        description = "Country data structure.")
data class Country(
                   @field:Schema(description = "Country ISO 3166 Code.",
                                     example = "US" ,
                                    required = true)
                   var countryId : String = "",
                   @field:Schema(description = "Country name (in English).",
                                     example = "USA" ,
                                    required = true)
                   var countryName : String = "") {
}