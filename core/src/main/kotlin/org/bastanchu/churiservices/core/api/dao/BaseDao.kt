package org.bastanchu.churiservices.core.api.dao

interface  BaseDao<K,E> {

    fun getById(id : K) : E?

    fun filter(filter : E) : List<E>

    fun listAll() : List<E>

    fun create(entity : E)

    fun deleteById(id : K)

    fun delete(entity : E)

    fun flush()

}