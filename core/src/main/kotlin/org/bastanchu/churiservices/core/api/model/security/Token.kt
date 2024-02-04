package org.bastanchu.churiservices.core.api.model.security

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Token",
        description = "Data content for token in use.")
data class Token(
    @field:Schema(description = "Token type to issue in authentication operations.",
                  example = "Bearer",
                  required = true)
    val type : String,
    @field:Schema(description = "Token content to place at Authorization http header.",
                  example = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJkaVJjZU5GMDlpdmlHeVA3bTR0aEgwMFczMWh4NUlCY1VrVEQxUUQwb0ZrIn0.eyJleHAiOjE3MDcwNzU1MzYsImlhdCI6MTcwNzA3NTIzNiwianRpIjoiNDkwOTI0MjEtNDg3Yi00MjBkLThkMjUtZDc1ZTQwMjhiNzVlIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDAwL2F1dGgvcmVhbG1zL0NodXJpc2VydmljZXNSZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiIyMWY3N2U0Mi1mZjc5LTQ3MTYtYWY3My01ZjFlZTQ0Nzk4ZTIiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJvcmRlcnMtYXBwIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLWNodXJpc2VydmljZXNyZWFsbSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoiZW1haWwgcHJvZmlsZSIsImNsaWVudEhvc3QiOiIxNzIuMTguMC4xIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtb3JkZXJzLWFwcCIsImNsaWVudEFkZHJlc3MiOiIxNzIuMTguMC4xIiwiY2xpZW50X2lkIjoib3JkZXJzLWFwcCJ9.FdYQ9dmgCpUK6nk6zcQLkCs9ey-kPKhXhTQr7-_n1j6YOmFRDa81DSk--ArojF14Fkz25m55JBqgsLgwlR_ZjAE2OtIxfVsAlEqRINln4dgVHXJcLBY9vvgJWatORhD6UwOVgDnY_FzA3jfIZnkdasX2jel1ZSvC8oDh9-3fx3SQKkonsGE8tNLH3W_rrjuJ4DCoiEZQHg04R2h8BW3wrjntAqLBE7YRjri_40UH0-Ny5UjyATEE3MGl04nd_F-GPAFyeDVnHO43bkb1AjZb2dkgzUD6dq2KOC6C7rbCk69FFfvfr5tEq8N591xGAxbAp24x_ZwzHVY3quITJnmmKQ",
                  required = true)
    val token : String,
    @field:Schema(description = "Timestamp for token expedition time.",
                  example = "2024-02-04 20:33:56 +0100",
                  required = true)
    val issueTimestamp : String,
    @field:Schema(description = "Timestamp for token expiry time.",
        example = "2024-02-04 20:38:56 +0100",
        required = true)
    val expiryTimestamp : String,
    @field:Schema(description = "client_id that identifies the system which issued this token.",
        example = "churiservices-app",
        required = true)
    val issuer : String
) {
}