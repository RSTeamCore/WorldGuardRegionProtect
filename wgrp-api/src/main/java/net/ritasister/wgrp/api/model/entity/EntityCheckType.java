package net.ritasister.wgrp.api.model.entity;


public interface EntityCheckType<E, T> {

    boolean check(E entity);

    T[] getEntityType();
}
