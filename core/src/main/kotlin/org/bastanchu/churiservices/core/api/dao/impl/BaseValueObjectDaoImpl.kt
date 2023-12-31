package org.bastanchu.churiservices.core.api.dao.impl

import jakarta.persistence.EntityManager
import org.bastanchu.churiservices.core.api.dao.BaseValueObjectDao
import org.bastanchu.churiservices.core.api.mapper.ValueObjectEntityMapper
import java.lang.reflect.ParameterizedType

abstract class BaseValueObjectDaoImpl<K,E,V>(override val entityManager: EntityManager,
                                    val valueObjectEntityMapper : ValueObjectEntityMapper<V,E>) :
                                                                                 BaseDaoImpl<K,E>(entityManager) ,
                                                                                 BaseValueObjectDao<K, E, V> {
    var valueObjectClassTypeClass : Class<V>? = null
    init {
        val genericSuperclass = this::class.java.genericSuperclass;
        if (genericSuperclass is ParameterizedType) {
            val parameterizedType : ParameterizedType = genericSuperclass
            valueObjectClassTypeClass = parameterizedType.actualTypeArguments[2] as Class<V>
        }
    }

    override fun toValueObject(entity: E): V {
        val emptyConstructor = valueObjectClassTypeClass!!.getDeclaredConstructor()
        val valueObjectInstance = emptyConstructor.newInstance()
        valueObjectEntityMapper.toValueObject(entity, valueObjectInstance)
        return valueObjectInstance
    }

    override fun toValueObjectList(entityList: List<E>): List<V> {
        return valueObjectEntityMapper.toValueObjectList(entityList)
    }

    override fun toValueObject(entity: E, valueObject : V) {
        valueObjectEntityMapper.toValueObject(entity, valueObject)
    }

    override fun fromValueObjectToEntity(valueObject : V): E {
        val emptyConstructor = entityClassTypeClass!!.getDeclaredConstructor()
        val unboundEntityInstance = emptyConstructor.newInstance()
        valueObjectEntityMapper.toEntity(valueObject, unboundEntityInstance)
        val entityKey = entityManager.entityManagerFactory.persistenceUnitUtil.getIdentifier(unboundEntityInstance) as K
        val boundEntity = getById(entityKey)
        if (boundEntity != null) {
            valueObjectEntityMapper.toEntity(valueObject, boundEntity)
            return boundEntity
        } else {
            return unboundEntityInstance
        }
    }

    override fun fromValueObjectToEntity(valueObject : V, entity: E) {
        valueObjectEntityMapper.toEntity(valueObject, entity)
    }

    override fun fromValueObjectToEntityList(valueObjectList : List<V>) : List<E> {
        return valueObjectEntityMapper.toEntityList(valueObjectList)
    }
}