package net.ritasister.wgrp.api.handler;

/**
 * Interface for implementing multiple loaders.
 * This is designed to handle loading operations for specific types of objects.
 *
 * @param <T> the type of object this handler will load.
 */
public interface LoadHandlers<T> {

    /**
     * Loads a handler for the specified class type.
     * Intended for use with Listeners and Commands.
     *
     * @param clazz the class instance to be loaded by this handler.
     */
    void loadHandler(T clazz);

}
