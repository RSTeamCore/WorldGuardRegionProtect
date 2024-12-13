package net.ritasister.wgrp.api.model.entity;

import net.ritasister.wgrp.api.model.location.Location;
import org.jetbrains.annotations.NotNull;

public interface Entity {

    /**
     * Gets the unique identifier for this entity.
     *
     * @return the unique identifier
     */
    String getUniqueId();

    /**
     * Gets the type of the entity.
     *
     * @return the type of the entity
     */
    EntityType getType();

    /**
     * Gets the current location of the entity.
     *
     * @return the entity's current location
     */
    Location getLocation();

    /**
     * Moves the entity to a specified location.
     *
     * @param location the destination location
     */
    void moveTo(Location location);

    /**
     * Checks if the entity is currently active.
     *
     * @return true if the entity is active, false otherwise
     */
    boolean isActive();

    /**
     * Enum representing the various types of entities.
     */
    enum EntityType {
        PLAYER,
        MOB,
        ITEM,
        PROJECTILE,
        VEHICLE,
        ANIMAL,
        NPC;

        /**
         * Gets the name of the entity type in lowercase.
         *
         * @return the name of the entity type in lowercase
         */
        public @NotNull String getName() {
            return name().toLowerCase();
        }
    }

}
