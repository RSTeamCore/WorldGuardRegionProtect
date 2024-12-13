package net.ritasister.wgrp.api.model.entity;

/**
 * Interface to validate entities using a provider for validation logic.
 *
 * @param <E> the type of the entity to be validated
 * @param <T> the type of the entity type for validation
 * @since 0.8.0
 */
public interface EntityCheckType<E, T> {

    /**
     * Checks if the specified entity meets the validation criteria.
     *
     * @param entity the entity to be validated
     * @return {@code true} if the entity passes the validation, {@code false} otherwise
     */
    boolean check(E entity);

    /**
     * Retrieves the supported entity types for validation.
     *
     * @return an array of supported entity types, or an empty array if none are available
     */
    T[] getEntityType();

}
