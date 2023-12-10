package org.bastanchu.churiservices.core.api.mapper

import org.bastanchu.churiservices.core.api.mapper.impl.BaseValueObjectEntityMapperDefaultImpl
import org.bastanchu.churiservices.core.api.model.PingStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
class BaseValueObjectEntityMapperTest {

    @Test
    fun `should map from value object to entity properly`() {
        val pingStatusValueObject = PingStatus(
            componentName = "status component",
            componentType = PingStatus.ComponentType.SPRING_BOOT_APP,
            version = "1.0",
            status = PingStatus.Status.RUNNING,
            timestamp = "2023-12-07 18:21:07 +0100"
        )
        val mapper = TestMapper()
        val pingStatusTestEntity = PingStatusTestEntity()
        mapper.toEntity(pingStatusValueObject, pingStatusTestEntity)
        assertEquals(pingStatusTestEntity.componentName, "status component")
        assertEquals(pingStatusTestEntity.componentType, PingStatus.ComponentType.SPRING_BOOT_APP)
        assertEquals(pingStatusTestEntity.version, "1.0")
        assertEquals(pingStatusTestEntity.status, PingStatus.Status.RUNNING)
        assertEquals(pingStatusTestEntity.timestamp, "2023-12-07 18:21:07 +0100")
    }

    @Test
    fun `should map from entity to value object properly`() {
        val pingStatusTestEntity = PingStatusTestEntity(
            componentName = "status component",
            componentType = PingStatus.ComponentType.SPRING_BOOT_APP,
            version = "1.0",
            status = PingStatus.Status.RUNNING,
            timestamp = "2023-12-07 18:21:07 +0100"
        )
        val mapper = TestMapper()
        val pingStatusValueObject = PingStatus(
            componentName = "",
            componentType = PingStatus.ComponentType.POSTGRESQL_DB,
            version = "",
            status = PingStatus.Status.NOT_AVAILABLE,
            timestamp = ""
        )
        mapper.toValueObject(pingStatusTestEntity, pingStatusValueObject)
        assertEquals(pingStatusValueObject.componentName, "status component")
        assertEquals(pingStatusValueObject.componentType, PingStatus.ComponentType.SPRING_BOOT_APP)
        assertEquals(pingStatusValueObject.version, "1.0")
        assertEquals(pingStatusValueObject.status, PingStatus.Status.RUNNING)
        assertEquals(pingStatusValueObject.timestamp, "2023-12-07 18:21:07 +0100")
    }

    @Test
    fun `should map from value object list to entity properly`() {
        val pingStatusValueObjectList = listOf(PingStatus(
            componentName = "status component",
            componentType = PingStatus.ComponentType.SPRING_BOOT_APP,
            version = "1.0",
            status = PingStatus.Status.RUNNING,
            timestamp = "2023-12-07 18:21:07 +0100"
        ),PingStatus(
            componentName = "status component DB",
            componentType = PingStatus.ComponentType.POSTGRESQL_DB,
            version = "2.0",
            status = PingStatus.Status.RUNNING,
            timestamp = "2023-12-31 18:21:07 +0100"
        ))
        val mapper = TestMapper()
        val entityList = mapper.toEntityList(pingStatusValueObjectList)
        val entity0 = entityList[0]
        val entity1 = entityList[1]
        assertEquals(entity0.componentName, "status component")
        assertEquals(entity0.componentType, PingStatus.ComponentType.SPRING_BOOT_APP)
        assertEquals(entity0.version, "1.0")
        assertEquals(entity0.status, PingStatus.Status.RUNNING)
        assertEquals(entity0.timestamp, "2023-12-07 18:21:07 +0100")
        assertEquals(entity1.componentName, "status component DB")
        assertEquals(entity1.componentType, PingStatus.ComponentType.POSTGRESQL_DB)
        assertEquals(entity1.version, "2.0")
        assertEquals(entity1.status, PingStatus.Status.RUNNING)
        assertEquals(entity1.timestamp, "2023-12-31 18:21:07 +0100")
    }

    @Test
    fun `should map from entity list to value object properly`() {
        val pingStatusEntityList = listOf(PingStatusTestEntity(
            componentName = "status component",
            componentType = PingStatus.ComponentType.SPRING_BOOT_APP,
            version = "1.0",
            status = PingStatus.Status.RUNNING,
            timestamp = "2023-12-07 18:21:07 +0100"
        ),PingStatusTestEntity(
            componentName = "status component DB",
            componentType = PingStatus.ComponentType.POSTGRESQL_DB,
            version = "2.0",
            status = PingStatus.Status.RUNNING,
            timestamp = "2023-12-31 18:21:07 +0100"
        ))
        val mapper = TestMapper()
        val valueObjectList = mapper.toValueObjectList(pingStatusEntityList)
        val valueObject0 = valueObjectList[0]
        val valueObject1 = valueObjectList[1]
        assertEquals(valueObject0.componentName, "status component")
        assertEquals(valueObject0.componentType, PingStatus.ComponentType.SPRING_BOOT_APP)
        assertEquals(valueObject0.version, "1.0")
        assertEquals(valueObject0.status, PingStatus.Status.RUNNING)
        assertEquals(valueObject0.timestamp, "2023-12-07 18:21:07 +0100")
        assertEquals(valueObject1.componentName, "status component DB")
        assertEquals(valueObject1.componentType, PingStatus.ComponentType.POSTGRESQL_DB)
        assertEquals(valueObject1.version, "2.0")
        assertEquals(valueObject1.status, PingStatus.Status.RUNNING)
        assertEquals(valueObject1.timestamp, "2023-12-31 18:21:07 +0100")
    }

    class TestMapper : BaseValueObjectEntityMapperDefaultImpl<PingStatus, PingStatusTestEntity>() {

    }
}