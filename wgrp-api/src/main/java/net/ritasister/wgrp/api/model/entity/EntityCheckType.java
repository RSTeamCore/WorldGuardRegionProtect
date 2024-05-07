package net.ritasister.wgrp.api.model.entity;

/**
 * Validate entities by using a provider to validate them.
 *
 * @param <E> the entity class type
 * @param <T> the entityType class
 * @since 0.8.0
 */
public interface EntityCheckType<E, T> {

    boolean check(E entity);

    T[] getEntityType();
}
