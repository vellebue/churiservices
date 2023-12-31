package org.bastanchu.churiservices.orders.internal.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "ORDER_LINES")
@IdClass(OrderLinePK::class)
class OrderLineEntity(
    @Id
    @Column(name = "ORDER_ID")
    var orderId : Int = 0,
    @Id
    @Column(name = "LINE_ID")
    var lineId : Int = 0,
    @Column(name = "ARTICLE_ID")
    var articleId : String = "",
    @Column(name = "ARTICLE_DESCRIPTION")
    var articleDescription : String = "",
    @Column(name = "NUM_ITEMS")
    var numItems : Int = 0,
    @Column(name = "BASE_PRICE")
    var basePrice : BigDecimal = BigDecimal(0),
    @Column(name = "VAT_TAX")
    var vatTax : BigDecimal = BigDecimal(0),
    @Column(name = "TOTAL_PRICE")
    var totalPrice : BigDecimal = BigDecimal(0),
    @Column(name = "TOTAL_VALUE")
    var totalValue : BigDecimal = BigDecimal(0)
) {
    override fun equals(other: Any?): Boolean {
        if (other is OrderLineEntity) {
            return orderId.equals(other.orderId) &&
                   lineId.equals(other.lineId)
        } else {
            return false
        }
    }

    override fun hashCode(): Int {
        var hashCode = 37 * orderId.hashCode() + 1
        hashCode = 37 * hashCode + lineId.hashCode()
        return hashCode
    }
}