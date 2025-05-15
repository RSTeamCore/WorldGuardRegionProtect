package net.ritasister.wgrp.api.model.location;

import java.util.UUID;

public interface World {

    /**
     * Gets the unique identifier of the world.
     *
     * @return the UUID of the world
     */
    UUID getUUID();

    /**
     * Gets the name of the world.
     *
     * @return the name of the world
     */
    String getName();

    /**
     * Gets the environment type of the world (e.g., NORMAL, NETHER, END).
     *
     * @return the environment type of the world
     */
    Environment getEnvironment();

    /**
     * Gets the difficulty level of the world.
     *
     * @return the difficulty level of the world
     */
    Difficulty getDifficulty();

    /**
     * Gets the spawn location in the world.
     *
     * @return the default spawn location
     */
    Location getSpawnLocation();

    /**
     * Enum representing the different environments of a world.
     */
    enum Environment {
        NORMAL,
        NETHER,
        END,
        CUSTOM
    }

    /**
     * Enum representing the difficulty levels of a world.
     */
    enum Difficulty {
        PEACEFUL,
        EASY,
        NORMAL,
        HARD
    }

}
