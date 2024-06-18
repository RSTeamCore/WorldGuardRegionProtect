package net.ritasister.wgrp.api.handler;

/**
 * This class used for Commands and Listeners.
 * @param <V>
 */
public interface Handler<V> {

    /**
     * Override if you use this method with parameters.
     */
    default void handle(V v) {

    }

    /**
     * Override if you use without a parameter only with Void.
     */
    default void handle() {

    }
}
