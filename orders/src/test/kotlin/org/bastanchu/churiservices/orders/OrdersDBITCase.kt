package org.bastanchu.churiservices.orders

import org.bastanchu.churiservices.coretest.api.test.MainDBITCase

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

class OrdersDBITCase(@Autowired applicationContext : ApplicationContext) : MainDBITCase(applicationContext) {

}