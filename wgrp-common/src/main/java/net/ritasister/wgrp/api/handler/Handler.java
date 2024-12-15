package net.ritasister.wgrp.api.handler;

/**
 * Interface for handling operations related to Commands and Listeners.
 * This can be implemented to perform actions either with or without parameters.
 *
 * @param <V> the type of object handled, or {@link Void} for parameter-less handling.
 */
public interface Handler<V> {

    /**
     * Handles an operation with a parameter.
     * Override this method to define behavior when a parameter of type {@code V} is provided.
     *
     * @param v the parameter of type {@code V} to handle.
     */
    default void handle(V v) {

    }

    /**
     * Handles a parameter-less operation.
     * Override this method to define behavior when no parameter is provided.
     */
    default void handle() {

    }
}
