package org.bastanchu.churiservices.core.api.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Envelop",
        description = "Generic common data envelope shared among all services.")
data class Envelope<T>(
                      @field:Schema(description = """Transaction Id defindes a trace id to mark all operations 
                          |                          made by this system across its transaction context.""",
                                        example = "b44870cbd05c43c695aa03a97fc20b0b3275452f8890761569e910c48d171fc8" ,
                                       required = false)
                      var transactionId : String = "",
                      @field:Schema(description = """Correlation id defines a global id not only for this transaction
                          |                          but also for some other operations in another systems derived from
                          |                          this service call.""",
                                        example = "caf075d027ad12365dce996fc8d647b74ec5b72defd7fea34009c8d3dc12fd29" ,
                                       required = false)
                      var correlationId : String = "",
                      @field:Schema(description = "Timestamp assigned by sending system",
                                        example = "2023-12-07 18:05:09 +0100",
                                       required = false)
                      var timestamp : String? = null,
                      @field:Schema(description = "Data content depending on specific service requirement.",
                                       required = true)
                      var data : T? = null) {
}