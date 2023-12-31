package org.bastanchu.churiservices.core.api.mapper

import java.math.BigDecimal

data class FullValueObject(val myNumber : Int = 0,
                           val myString: String = "",
                           val myBigDecimal: BigDecimal = BigDecimal.ZERO) {
}