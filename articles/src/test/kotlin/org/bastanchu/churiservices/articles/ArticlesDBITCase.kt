package org.bastanchu.churiservices.articles

import org.bastanchu.churiservices.coretest.api.test.MainDBITCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

class ArticlesDBITCase(@Autowired applicationContext : ApplicationContext) : MainDBITCase(applicationContext) {
}