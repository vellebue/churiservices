package org.bastanchu.churiservices.core.api.model.article

import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "ArticlePricingCondition",
        description = "Article Pricing Condition required to calculate article value.")
class ArticlePricingCondition(@field:Schema(description = "Pricing condition type (Fare, Tax, Discount...).",
                                            example = "FARE",
                                            required = true)
                              val type : PricingConditionType,
                              @field:Schema(description = """Order to evaluate pricing condition:
                                  | pricing conditions are evaluated in ascending order according to this
                                  | indicator. Relative conditions are refered to less (strict) inmediate 
                                  | accumulated value.
                              """,
                                            example = "1",
                                            required = true)
                              val order : Int,
                              @field:Schema(description = """
                                  Pricing condition kind:
                                  | VALUE: Adds or increments previous value condition with this value.
                                  | PERCENTAGE: Adds a certain percentage according to previous value
                              """,
                                  example = "VALUE",
                                  required = true)
                              val valueType : PricingConditionKind,
                              @field:Schema(description = "Pricing condition value, it may be an absolute value or a percentage value.",
                                            example = "7.2",
                                            required = true)
                              val value : Double) {

    enum class PricingConditionType(val type : String) {
        FARE("FARE"),
        VAT_TAX("VAT_TAX"),
        LINE_DISCOUNT("LINE_DISCOUNT");

        @JsonValue
        override fun toString() : String {
            return type
        }
    }

    enum class PricingConditionKind(val type : String) {
        PERCENTAGE("PERCENTAGE"),
        VALUE("VALUE");

        @JsonValue
        override fun toString() : String {
            return type
        }
    }
}