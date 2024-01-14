package org.bastanchu.churiservices.core.api.mapper.impl

import org.bastanchu.churiservices.core.api.mapper.ValueObjectEntityMapper
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.math.BigDecimal

abstract class BaseValueObjectEntityMapperDefaultImpl<V,E> : ValueObjectEntityMapper<V, E> {

    val BASIC_TYPES = arrayOf(Int::class.java, Integer::class.java, String::class.java, BigDecimal::class.java)

    val valueObjectClassType = getParameterizedType<V>(0)
    val entityClassType = getParameterizedType<E>(1)

    override fun toValueObject(entity: E, valueObject : V) {
        assign(entity, entityClassType, valueObject, valueObjectClassType)
    }

    override fun toEntity(valueObject: V, entity : E) {
        assign(valueObject, valueObjectClassType, entity, entityClassType)
    }

    override fun toValueObjectList(entityList: List<E>): List<V> {
        return entityList.map {
            val valueObject = buildInstance(valueObjectClassType)
            toValueObject(it, valueObject)
            valueObject
        }
    }

    override fun toEntityList(valueObjectList: List<V>): List<E> {
        return valueObjectList.map {
            val entity = buildInstance(entityClassType)
            toEntity(it, entity)
            entity
        }
    }

    private fun<T> buildInstance(classInstance : Class<T>) : T {
        val emptyConstructor = classInstance.getDeclaredConstructor()
        val instance = emptyConstructor.newInstance()
        return instance
    }

    private fun <S, T> assign(source : S, sourceClass : Class<S>,
                              target : T, targetClass : Class<T>) {
        val targetFields = targetClass.declaredFields
        for (targetField in targetFields) {
            val candidateSourceField = getMatchingField(targetField, sourceClass)
            if (candidateSourceField != null)
                if ((BASIC_TYPES.filter { candidateSourceField.type.isAssignableFrom(it) }.size > 0) ||
                    candidateSourceField.type.isEnum) {
                    //Is basic type or enum
                    candidateSourceField.trySetAccessible()
                    val value = candidateSourceField.get(source)
                    targetField.trySetAccessible()
                    targetField.set(target, value)
                }
        }
    }

    fun getMatchingField(sourceField : Field, targetClass : Class<*>) : Field? {
        val targetFieldList = targetClass.declaredFields.filter {
            (it.name == sourceField.name) && it.type.isAssignableFrom(sourceField.type)
        }
        if (targetFieldList.size > 0) {
            return targetFieldList[0]
        } else {
            return null
        }
    }

    fun <T> getParameterizedType(order : Int) : Class<T> {
        val genericSuperclass = this::class.java.genericSuperclass;
        if (genericSuperclass is ParameterizedType) {
            val parameterizedType : ParameterizedType = genericSuperclass
            return parameterizedType.actualTypeArguments[order] as Class<T>
        } else {
            throw ClassNotFoundException("No parameterized class defined (this may not happen)")
        }
    }
}