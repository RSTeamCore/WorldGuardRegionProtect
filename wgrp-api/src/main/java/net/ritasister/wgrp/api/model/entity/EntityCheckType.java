package net.ritasister.wgrp.api.model.entity;

/**
 * @param <E>
 * @param <T>
 */
public interface EntityCheckType<E, T> {

    boolean check(E entity);

    T[] getEntityType();
}
