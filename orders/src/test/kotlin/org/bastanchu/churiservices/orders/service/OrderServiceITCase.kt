package org.bastanchu.churiservices.orders.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.coretest.api.test.BaseITCase
import org.bastanchu.churiservices.orders.internal.entity.AddressEntity
import org.bastanchu.churiservices.orders.internal.entity.OrderHeaderEntity
import org.bastanchu.churiservices.orders.internal.entity.OrderLineEntity
import org.bastanchu.churiservices.orders.internal.service.OrderService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Order
import org.springframework.beans.factory.annotation.Autowired

import java.math.BigDecimal
import java.sql.Connection

class OrderServiceITCase(@Autowired val orderService : OrderService,
                         @Autowired val objectMapper : ObjectMapper) : BaseITCase() {

    override fun getScriptContent(): String {
        return """
            select nextval('seq_orders');
            select nextval('seq_addresses');
            select nextval('seq_addresses');
            INSERT INTO addresses
            (address_id, address, zip_code, city, region_id, country_id)
            VALUES(0, 'Howard St 21', '94105', 'San Francisco', 'CA', 'US');
            INSERT INTO addresses
            (address_id, address, zip_code, city, region_id, country_id)
            VALUES(1, '7th Street 12 4 1', '94105', 'San Francisco', 'CA', 'US');
            INSERT INTO order_headers
            (order_id, customer_order_id, customer_id, delivery_address_id, invoice_address_id, base_value, vat_tax_value, total_value)
            VALUES(0, 'ACV-EA9172', '00018122232', 0, 1, 371.26, 152.34, 523.60);
            INSERT INTO order_lines
            (order_id, line_id, article_id, article_description, num_items, base_price, vat_tax, total_price, total_value)
            VALUES(0, 0, 'ORTE37553', 'Compaq Laptop Multimedia Series 17".', 1, 371.26, 152.34, 523.60, 523.61);
            commit;
        """.trimIndent()
    }

    @Test
    @Order(1)
    fun `should get an existing order properly`() {
        val order = orderService.getOrder(0)
        // Order Header
        assertNotNull(order)
        assertEquals("ACV-EA9172", order?.customerOrderId)
        assertEquals("00018122232", order?.customerId)
        assertEquals(BigDecimal("371.26"), order?.baseValue)
        assertEquals(BigDecimal("152.34"), order?.vatTaxValue)
        assertEquals(BigDecimal("523.60"), order?.totalValue)
        // Delivery Address
        val deliveryAddress = order?.deliveryAdress
        assertNotNull(deliveryAddress)
        assertEquals("Howard St 21",deliveryAddress?.address)
        assertEquals("94105", deliveryAddress?.zipCode)
        assertEquals("San Francisco", deliveryAddress?.city)
        assertEquals("CA", deliveryAddress?.regionId)
        assertEquals("California", deliveryAddress?.regionName)
        assertEquals("US", deliveryAddress?.countryId)
        assertEquals("United States of America", deliveryAddress?.countryName)
        // Invoice Address
        val invoiceAddress = order?.invoiceAddress
        assertNotNull(invoiceAddress)
        assertEquals("7th Street 12 4 1",invoiceAddress?.address)
        assertEquals("94105", invoiceAddress?.zipCode)
        assertEquals("San Francisco", invoiceAddress?.city)
        assertEquals("CA", invoiceAddress?.regionId)
        assertEquals("California", invoiceAddress?.regionName)
        assertEquals("US", invoiceAddress?.countryId)
        assertEquals("United States of America", invoiceAddress?.countryName)
        // Order Line
        val line = order?.lines?.get(0)
        assertNotNull(line)
        assertEquals("ORTE37553", line?.articleId)
        assertEquals("Compaq Laptop Multimedia Series 17\".", line?.articleDescription)
        assertEquals(1, line?.numItems)
        assertEquals(BigDecimal("371.260"), line?.basePrice)
        assertEquals(BigDecimal("152.340"), line?.vatTax)
        assertEquals(BigDecimal("523.600"), line?.totalPrice)
        assertEquals(BigDecimal("523.610"), line?.totalValue)
    }

    @Test
    @Order(2)
    fun `should fail when getting from non existing order`() {
        val order = orderService.getOrder(100)
        assertNull(order)
    }

    @Test
    @Order(3)
    fun `should create a complete order correctly`() {
        val classLoader = Thread.currentThread().contextClassLoader
        val orderDataStream = classLoader.getResourceAsStream("org/bastanchu/churiservices/orders/service/createOrderData.json")
        orderDataStream.use {
            val order = objectMapper.readValue(it, OrderHeader::class.java)
            assertNotNull(order)
            orderService.createOrder(order)
            assertEquals(1, order.orderId)
            val orderHeaderEntity = retrieveOrderHeaderEntity(order.orderId!!)!!
            //Check order header
            assertEquals(order.customerOrderId, orderHeaderEntity.customerOrderId)
            assertEquals(order.customerId, orderHeaderEntity.customerId)
            assertEquals(order.baseValue, orderHeaderEntity.baseValue)
            assertEquals(order.baseValue, orderHeaderEntity.baseValue)
            assertEquals(order.vatTaxValue, orderHeaderEntity.vatTaxValue)
            assertEquals(order.totalValue, orderHeaderEntity.totalValue)
            //Check delivery address
            val deliveryAddress = orderHeaderEntity.deliveryAdress
            assertEquals(order.deliveryAdress.address, deliveryAddress.address)
            assertEquals(order.deliveryAdress.zipCode, deliveryAddress.zipCode)
            assertEquals(order.deliveryAdress.city, deliveryAddress.city)
            assertEquals(order.deliveryAdress.countryId, deliveryAddress.countryId)
            assertEquals(order.deliveryAdress.regionId, deliveryAddress.regionId)
            // Check invoice address
            val invoiceAddress = orderHeaderEntity.invoiceAddress
            assertEquals(order.invoiceAddress.address, invoiceAddress.address)
            assertEquals(order.invoiceAddress.zipCode, invoiceAddress.zipCode)
            assertEquals(order.invoiceAddress.city, invoiceAddress.city)
            assertEquals(order.invoiceAddress.countryId, invoiceAddress.countryId)
            assertEquals(order.invoiceAddress.regionId, invoiceAddress.regionId)
            // Check line
            val orderLine = order.lines[0]
            assertNotNull(order.lines)
            assertEquals(1, orderHeaderEntity.lines.size)
            val orderLineEntity = orderHeaderEntity.lines[0]
            assertEquals(orderLine.articleId, orderLineEntity.articleId)
            assertEquals(orderLine.articleDescription, orderLineEntity.articleDescription)
            assertEquals(orderLine.numItems, orderLineEntity.numItems)
            assertEquals(orderLine.basePrice, orderLineEntity.basePrice)
            assertEquals(orderLine.vatTax, orderLineEntity.vatTax)
            assertEquals(orderLine.totalPrice, orderLineEntity.totalPrice)
            assertEquals(orderLine.totalValue, orderLineEntity.totalValue)
        }
    }

    private fun retrieveOrderHeaderEntity(order_id : Int) : OrderHeaderEntity? {
        val sql = """ 
            select order_id, customer_order_id, customer_id, delivery_address_id, invoice_address_id, 
                   base_value, vat_tax_value, total_value
            from ORDER_HEADERS
            where order_id = ?
        """.trimIndent()
        val con = dataSource?.connection!!
        con.use {
            val statement = it.prepareStatement(sql)
            statement.setInt(1, order_id)
            statement.use {
                val resultSet = it.executeQuery()
                resultSet.use {
                    if (it.next()) {
                        val entity = OrderHeaderEntity()
                        entity.orderId = it.getInt("order_id")
                        entity.customerOrderId = it.getString("customer_order_id")
                        entity.customerId = it.getString("customer_id")
                        entity.baseValue = it.getBigDecimal("base_value")
                        entity.vatTaxValue = it.getBigDecimal("vat_tax_value")
                        entity.totalValue = it.getBigDecimal("total_value")
                        entity.deliveryAdress = retrieveAddress(con, it.getInt("delivery_address_id"))!!
                        entity.invoiceAddress = retrieveAddress(con, it.getInt("invoice_address_id"))!!
                        entity.lines = retrieveOrderLines(con, entity.orderId!!)
                        return entity
                    }
                }
            }
        }
        return null
    }

    private fun retrieveAddress(con : Connection, addressId : Int) : AddressEntity? {
        val sql = """
            select a.address_id, a.address, a.zip_code, a.city, a.region_id, r.region_name, a.country_id, c.country_name
            from ADDRESSES a, COUNTRIES c, REGIONS r
            where address_id = ? and a.country_id = c.country_id and a.region_id = r.region_id
        """.trimIndent()
        val statement = con.prepareStatement(sql)
        statement.setInt(1, addressId)
        statement.use {
            val resultSet = it.executeQuery()
            resultSet.use {
                if (it.next()) {
                    val entity = AddressEntity()
                    entity.addressId = it.getInt("address_id")
                    entity.address = it.getString("address")
                    entity.zipCode = it.getString("zip_code")
                    entity.city = it.getString("city")
                    entity.regionId = it.getString("region_id")
                    entity.countryId = it.getString("country_id")
                    return entity
                }
            }
        }
        return null
    }

    private fun retrieveOrderLines(con : Connection, orderId: Int) : MutableList<OrderLineEntity> {
        val sql = """
            select order_id, line_id, article_id, article_description, num_items, base_price, 
                   vat_tax, total_price, total_value
            from ORDER_LINES
            where order_id = ?
            order by line_id asc
        """.trimIndent()
        val statement = con.prepareStatement(sql)
        statement.use {
            it.setInt(1, orderId)
            val resultSet = it.executeQuery()
            resultSet.use {
                val result = ArrayList<OrderLineEntity>()
                while (it.next()) {
                    val line = OrderLineEntity()
                    line.orderId = it.getInt("order_id")
                    line.lineId = it.getInt("line_id")
                    line.articleId = it.getString("article_id")
                    line.articleDescription = it.getString("article_description")
                    line.numItems = it.getInt("num_items")
                    line.basePrice = it.getBigDecimal("base_price")
                    line.vatTax = it.getBigDecimal("vat_tax")
                    line.totalPrice = it.getBigDecimal("total_price")
                    line.totalValue = it.getBigDecimal("total_value")
                    result.add(line)
                }
                return result
            }
        }
        return ArrayList()
    }
}