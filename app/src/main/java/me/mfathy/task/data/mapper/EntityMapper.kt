package me.mfathy.task.data.mapper


/**
 * Mapper contract to convert and map data entities.
 */
interface EntityMapper<E, D> {
    fun mapFromEntity(entity: E): D
    fun mapToEntity(domain: D): E
}