package net.ritasister.wgrp.api.config;

/**
 * Interface for providing message configurations.
 *
 * @param <P> The type of the plugin instance.
 * @param <M> The type of the messages' configuration.
 */
public interface MessageProvider<P, M> {

    /**
     * Initializes the message provider with the given plugin instance.
     *
     * @param plugin The plugin instance.
     */
    void init(P plugin);

    /**
     * Retrieves the messages configuration.
     *
     * @return The messages' configuration.
     */
    M getMessages();
}
