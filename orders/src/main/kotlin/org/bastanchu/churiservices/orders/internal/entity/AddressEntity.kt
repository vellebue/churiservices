package org.bastanchu.churiservices.orders.internal.entity

import jakarta.persistence.*

@Entity
@Table(name = "ADDRESSES")
data class AddressEntity(
                    @Id
                    @Column(name = "ADDRESS_ID")
                    @GeneratedValue(generator = "SEQ_ADDRESS")
                    @SequenceGenerator(name = "SEQ_ADDRESS", sequenceName = "SEQ_ADDRESSES", allocationSize = 1)
                    var addressId : Int = 0,
                    @Column(name = "ADDRESS")
                    var address : String = "",
                    @Column(name = "ZIP_CODE")
                    var zipCode : String = "",
                    @Column(name = "CITY")
                    var city : String = "",
                    @Column(name = "REGION_ID")
                    var regionId : String = "",
                    @Column(name = "COUNTRY_ID")
                    var countryId : String = "",
                    ) {
}