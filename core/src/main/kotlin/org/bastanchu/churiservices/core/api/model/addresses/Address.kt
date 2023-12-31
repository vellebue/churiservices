package org.bastanchu.churiservices.core.api.model.addresses

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Address",
        description = "Data structure which represents Addresses.")
data class Address(
                   @field:Schema(description = "Address description in local language (Street, building id...)",
                                     example = "Calle Mayor 21 Esc. Izq. 9º B" ,
                                    required = true)
                   var address : String = "",
                   @field:Schema(description = "ZIP code in local format",
                                     example = "28922" ,
                                    required = true)
                   var zipCode : String = "",
                   @field:Schema(description = "City name for address in local language",
                                     example = "Alcorcón" ,
                                    required = true)
                   var city : String = "",
                   @field:Schema(description = "Region Id for address according to local convention.",
                                     example = "28" ,
                                    required = true)
                   var regionId : String = "",
                   @field:Schema(description = "Region name in local language.",
                                     example = "MADRID" ,
                                    required = false)
                   var regionName : String = "",
                   @field:Schema(description = "Country ISO 3166 Code.",
                                     example = "ES" ,
                                    required = true)
                   var countryId : String = "",
                   @field:Schema(description = "Country name in English.",
                                     example = "Spain" ,
                                    required = false)
                   var countryName : String = "") {
}