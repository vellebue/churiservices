package org.bastanchu.churiservices.orders.internal.entity

import jakarta.persistence.*

@Entity
@Table(name = "REGIONS")
@IdClass(RegionPK::class)
data class RegionEntity(
    @Id
    @Column(name = "COUNTRY_ID")
    var countryId : String = "",
    @Id
    @Column(name = "REGION_ID")
    var regionId : String = "",
    @Column(name = "REGION_NAME")
    var regionName : String = ""
) {

    override fun equals(other: Any?): Boolean {
        if (other is RegionEntity) {
            return countryId.equals(other.countryId) &&
                   regionId.equals(other.regionId)
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var hashCode = 37 * countryId.hashCode() + 1
        hashCode = 37 * hashCode + regionId.hashCode()
        return hashCode
    }
}