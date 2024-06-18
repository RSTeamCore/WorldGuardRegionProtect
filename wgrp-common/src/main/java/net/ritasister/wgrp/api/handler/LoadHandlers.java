package net.ritasister.wgrp.api.handler;

/**
 * This class used for implements multiple loaders.
 * @param <T>
 */
public interface LoadHandlers<T> {

    /**
     * Only used for loaders in Listeners and Commands.
     */
    void loadHandler(T clazz);

}
