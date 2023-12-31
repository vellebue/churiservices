package org.bastanchu.churiservices.orders.internal.entity

import jakarta.persistence.*

import java.math.BigDecimal


@Entity
@Table(name = "ORDER_HEADERS")
data class OrderHeaderEntity(
    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue(generator = "SEQ_ORDER")
    @SequenceGenerator(name = "SEQ_ORDER", sequenceName = "SEQ_ORDERS", allocationSize = 1)
    var orderId : Int = 0,
    @Column(name = "CUSTOMER_ORDER_ID")
    var customerOrderId : String = "",
    @Column(name = "CUSTOMER_ID")
    var customerId : String = "",
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "DELIVERY_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    var deliveryAdress : AddressEntity = AddressEntity(),
    @OneToOne(cascade = arrayOf(CascadeType.ALL))
    @JoinColumn(name = "INVOICE_ADDRESS_ID", referencedColumnName = "ADDRESS_ID")
    var invoiceAddress : AddressEntity = AddressEntity(),
    @Column(name = "BASE_VALUE")
    var baseValue : BigDecimal = BigDecimal(0),
    @Column(name = "VAT_TAX_VALUE")
    var vatTaxValue : BigDecimal = BigDecimal(0),
    @Column(name = "TOTAL_VALUE")
    var totalValue : BigDecimal = BigDecimal(0),
    @OneToMany(cascade = arrayOf(CascadeType.ALL),
               orphanRemoval = true)
    @JoinColumn(name = "ORDER_ID")
    @OrderColumn(name = "LINE_ID")
    var lines : MutableList<OrderLineEntity> = ArrayList()
) {
}