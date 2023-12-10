package org.bastanchu.churiservices.core.api.mapper

interface ValueObjectEntityMapper<V,E> {

    fun toValueObject(entity : E, valueObject : V)

    fun toEntity(valueObject : V, entity :E)

    fun toValueObjectList(entityList : List<E>) : List<V>

    fun toEntityList(valueObjectList : List<V>) : List<E>

}