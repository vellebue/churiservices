package org.bastanchu.churiservices.orders.internal.service.impl

import org.bastanchu.churiservices.core.api.model.orders.OrderHeader
import org.bastanchu.churiservices.orders.internal.dao.*
import org.bastanchu.churiservices.orders.internal.entity.RegionPK
import org.bastanchu.churiservices.orders.internal.service.OrderService
import org.bastanchu.churiservices.orders.internal.service.OrdersQueueSenderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
class OrderServiceImpl(@Autowired val ordersQueueSenderService: OrdersQueueSenderService,
                       @Autowired val environment: Environment,
                       @Autowired val orderHeaderDao : OrderHeaderDao,
                       @Autowired val orderLineDao : OrderLineDao,
                       @Autowired val addressDao: AddressDao,
                       @Autowired val countryDao: CountryDao,
                       @Autowired val regionDao : RegionDao) : OrderService {
    override fun createOrder(order: OrderHeader) {
        val orderHeaderEntity = orderHeaderDao.fromValueObjectToEntity(order)
        orderHeaderEntity.deliveryAdress = addressDao.fromValueObjectToEntity(order.deliveryAdress)
        orderHeaderEntity.invoiceAddress = addressDao.fromValueObjectToEntity(order.invoiceAddress)
        var numLine = 0
        orderHeaderDao.create(orderHeaderEntity)
        order.lines.forEach {
            val orderLineEntity = orderLineDao.fromValueObjectToEntity(it)
            orderLineEntity.orderId = orderHeaderEntity.orderId
            orderLineEntity.lineId = numLine
            numLine++
            orderHeaderEntity.lines.add(orderLineEntity)
        }
        ordersQueueSenderService.send(environment.getProperty("org.bastanchu.churiservices.rabbitmq.routingkeys.orderstodeliveries")!!
            ,order)
        orderHeaderDao.toValueObject(orderHeaderEntity, order)
    }

    override fun getOrder(orderId: Int): OrderHeader? {
        val orderHeaderEntity = orderHeaderDao.getById(orderId)
        if (orderHeaderEntity != null) {
            val orderHeader = orderHeaderDao.toValueObject(orderHeaderEntity)
            orderHeader.deliveryAdress = addressDao.toValueObject(orderHeaderEntity.deliveryAdress)
            orderHeader.deliveryAdress.countryName = countryDao.getById(orderHeader.deliveryAdress.countryId)?.countryName ?: ""
            orderHeader.deliveryAdress.regionName = regionDao.getById(RegionPK(orderHeader.deliveryAdress.countryId,
                                                                               orderHeader.deliveryAdress.regionId))?.regionName ?: ""
            orderHeader.invoiceAddress = addressDao.toValueObject(orderHeaderEntity.invoiceAddress)
            orderHeader.invoiceAddress.countryName = countryDao.getById(orderHeader.invoiceAddress.countryId)?.countryName ?: ""
            orderHeader.invoiceAddress.regionName = regionDao.getById(RegionPK(orderHeader.invoiceAddress.countryId,
                                                                               orderHeader.invoiceAddress.regionId))?.regionName ?: ""
            orderHeaderEntity.lines.forEach {
                orderHeader.lines.add(orderLineDao.toValueObject(it))
            }
            return orderHeader
        } else {
            return null
        }
     }
}