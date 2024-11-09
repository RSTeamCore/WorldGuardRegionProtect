package net.ritasister.wgrp.api.platform;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Platform {

    /**
     * Gets the type of platform LuckPerms is running on
     *
     * @return the type of platform LuckPerms is running on
     */
    Platform.@NonNull Type getType();

    /**
     * Represents a type of platform which WorldGuardRegionProtect can run on.
     */
    enum Type {
        SPIGOT("Spigot"),
        PAPER("Paper"),
        FOLIA("Folia"),
        UNKNOWN("Unknown");

        private final String platform;

        Type(String platform) {
            this.platform = platform;
        }

        /**
         * Gets a readable name for the platform type.
         *
         * @return a readable name
         */
        public @NonNull String getPlatformName() {
            return this.platform;
        }
    }

}
