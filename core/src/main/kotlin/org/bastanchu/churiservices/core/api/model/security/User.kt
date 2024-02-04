package org.bastanchu.churiservices.core.api.model.security

import com.fasterxml.jackson.annotation.JsonValue
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "User",
        description = "User data relative to user session.")
data class User(
   @field:Schema(description = "Login user or client_id that identifies user in session.",
                 example = "john",
                 required = true)
   val login : String,
   @field:Schema(description = "Name of the user or client_id for session user.",
                 example = "John Doe",
                 required = true)
   val name : String,
   @field:Schema(description = "Email for user in session if defined.",
                 example = "johndoe@gmail.com",
                 required = true)
   val email : String,
   @field:Schema(description = "Kind of user, regular user (a person) or another system (system user)",
                 example = "regularUser",
                 required = true)
   val type : Type,
   @field:Schema(description = "Token data for user in session.",
                 required = true)
   val token : Token,
   @field:Schema(description = "Roles list for user in session",
                 required = true)
   val roles : List<Role> = ArrayList<Role>()
) {
    enum class Type (val type : String) {

        REGULAR_USER("regularUser"),
        SYSTEM_USER("systemUser");

        @JsonValue
        override fun toString() : String {
            return type
        }
    }
}