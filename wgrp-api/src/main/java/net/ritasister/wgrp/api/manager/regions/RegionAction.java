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
    RegionAction.@NonNull Type getType();

    /**
     * Represents the various types of actions that can be performed in a region.
     */
    enum Type {
        /**
         * Represents the action of breaking within a region.
         */
        BREAK("break"),

        /**
         * Represents the action of placing within a region.
         */
        PLACE("place"),

        /**
         * Represents the action of interacting within a region.
         */
        INTERACT("interact");

        private final String action;

        /**
         * Constructs a Type with the specified action name.
         *
         * @param action The name of the action.
         */
        Type(String action) {
            this.action = action;
        }

        /**
         * Retrieves the name of the action represented by this type.
         *
         * @return The name of the action.
         */
        public String getAction() {
            return this.action;
        }
    }

}
