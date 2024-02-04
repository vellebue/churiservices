package org.bastanchu.churiservices.core.api.model.security

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Role",
        description = "Role assignable to users to grant permissions for systems.")
data class Role(
    @field:Schema(description = "Name of the role",
                  example = "ARTICLES",
                  required = true)
    val name : String,
    @field:Schema(description = "Description for this role",
                  example = "Role to grant acces for articles microservice operations.",
                  required = true)
    val description : String
) {
}