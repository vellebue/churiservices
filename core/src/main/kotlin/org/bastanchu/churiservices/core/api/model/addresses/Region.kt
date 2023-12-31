package org.bastanchu.churiservices.core.api.model.addresses

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Region",
        description = "Region data structure.")
data class Region(
                  @field:Schema(description = "Country ISO 3166 Code.",
                                example = "ES" ,
                                required = true)
                  var countryId : String = "",
                  @field:Schema(description = "Region code according to local conventions.",
                      example = "28" ,
                      required = true)
                  var regionId : String = "",
                  @field:Schema(description = "Region name in local reference language.",
                                    example = "Madrid" ,
                                   required = true)
                  var regionName : String = "") {
}