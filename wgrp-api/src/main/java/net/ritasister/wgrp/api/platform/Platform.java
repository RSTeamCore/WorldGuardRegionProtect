package net.ritasister.wgrp.api.platform;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface Platform {

    /**
     * Retrieves the type of platform on which WorldGuardRegionProtect is running.
     *
     * @return the platform type as an enum value from {@link Platform.Type}.
     * @since 1.2.1.21
     */
    Platform.@NonNull Type getType();

    /**
     * Represents the various types of platforms on which WorldGuardRegionProtect can run.
     * Each platform type corresponds to a different server environment.
     */
    enum Type {
        /**
         * Represents the Bukkit platform.
         * Typically used for traditional Minecraft server setups.
         */
        BUKKIT("Bukkit"),

        /**
         * Represents the Spigot platform.
         * A performance-optimized version of Bukkit, widely used in the Minecraft community.
         */
        SPIGOT("Spigot"),

        /**
         * Represents the Paper platform.
         * A high-performance fork of Spigot, providing additional optimizations and features.
         */
        PAPER("Paper"),

        /**
         * Represents the Folia platform.
         * A newer, multithreaded Minecraft server solution designed for better performance.
         */
        FOLIA("Folia"),

        /**
         * Represents an unknown or unsupported platform.
         * Used as a fallback if the platform cannot be identified.
         */
        UNKNOWN("Unknown");

        private final String platform;

        Type(String platform) {
            this.platform = platform;
        }

        /**
         * Retrieves a human-readable name for the platform type.
         *
         * @return the name of the platform type.
         * @since 1.2.1.21
         */
        public @NonNull String getPlatformName() {
            return this.platform;
        }
    }
}
