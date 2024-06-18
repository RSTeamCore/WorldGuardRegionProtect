package net.ritasister.wgrp.api.model.entity;

/**
 * Validate entities by using a provider to validate them.
 *
 * @param <E> the entity class type
 * @param <T> the entityType class
 * @since 0.8.0
 */
public interface EntityCheckType<E, T> {

    /**
     *
     * @param entity what is entity check.
     * @return entity type what is checked.
     */
    boolean check(E entity);

    /**
     * Get an entity type for checking in regions or whatever.
     * @return entityType.
     */
    T[] getEntityType();
}
