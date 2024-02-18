package org.bastanchu.churiservices.articles.internal.entity

import jakarta.persistence.*

@Entity
@Table(name = "FORMATS")
data class FormatEntity(
    @Id
    @Column(name = "FORMAT_ID")
    val formatId: String = "",
    @Column(name = "DESCRIPTION")
    var description: String = "") {
}