package net.ritasister.wgrp.api.manager.regions;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Defines different types of interactions with a region.
 */
public interface RegionAction {

    /**
     * Retrieves the type of the region action.
     *
     * @return The type of the region action.
     */
    @NonNull Type getType();

    /**
     * Represents the various types of actions that can be performed in a region.
     */
    @FunctionalInterface
    interface Type {

        /**
         * Return name of the type of the action.
         */
        String getName();

        /**
         * Represents the action of breaking within a region.
         */
        Type BREAK = () -> "break";

        /**
         * Represents the action of placing within a region.
         */
        Type PLACE = () -> "place";

        /**
         * Represents the action of interacting within a region.
         */
        Type INTERACT = () -> "interact";

    }

}
