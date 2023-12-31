package org.bastanchu.churiservices.orders.internal.entity

import java.io.Serializable

data class RegionPK(var countryId : String = "",
                    var regionId : String = "") : Serializable {
}