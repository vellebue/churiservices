package org.bastanchu.churiservices.orders.internal.entity

import jakarta.persistence.*

@Entity
@Table(name = "COUNTRIES")
data class CountryEntity(
                         @Id
                         @Column(name = "COUNTRY_ID")
                         var countryId : String = "",
                         @Column(name = "COUNTRY_NAME")
                         var countryName : String = "") {
}