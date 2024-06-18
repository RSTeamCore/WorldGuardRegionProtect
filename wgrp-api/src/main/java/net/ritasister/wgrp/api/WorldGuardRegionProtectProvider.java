package net.ritasister.wgrp.api;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;

/**
 * The WorldGuardRegionProtect API.
 *
 * <p>Provider to get the WorldGuardRegionProtect api instance.</p>
 */
public final class WorldGuardRegionProtectProvider {

    private static WorldGuardRegionProtect instance;

    @ApiStatus.Internal
    private WorldGuardRegionProtectProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
    }

    /**
     * Set instance of this api.
     * @param worldGuardRegionProtect of this plugin.
     */
    @ApiStatus.Internal
    public static void setWorldGuardRegionProtect(final WorldGuardRegionProtect worldGuardRegionProtect) {
        if (WorldGuardRegionProtectProvider.instance != null) {
            throw new IllegalArgumentException("WorldGuardRegionProtectProvider is already set!");
        }
        WorldGuardRegionProtectProvider.instance = worldGuardRegionProtect;
    }

    /**
     * Gets an instance of the {@link WorldGuardRegionProtect} API,
     * throwing {@link IllegalStateException} if the API is not loaded yet.
     *
     * <p>This method will never return null.</p>
     *
     * @return an instance of the WorldGuardRegionProtect API
     * @throws IllegalStateException if the API is not loaded yet
     */
    public static @NonNull WorldGuardRegionProtect get() {
        final WorldGuardRegionProtect instance = WorldGuardRegionProtectProvider.instance;
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    /**
     * Exception thrown when the API is requested before it has been loaded.
     */
    private static final class NotLoadedException extends IllegalStateException {

        private static final String MESSAGE = "The WorldGuardRegionProtect API isn't loaded yet!\n" +
                "This could be because:\n" +
                "  a) the WorldGuardRegionProtect plugin is not installed or it failed to enable\n" +
                "  b) the plugin in the stacktrace does not declare a dependency on WorldGuardRegionProtect\n" +
                "  c) the plugin in the stacktrace is retrieving the API before the plugin 'enable' phase\n" +
                "     (call the #get method in onEnable, not the constructor!)\n";

        NotLoadedException() {
            super(MESSAGE);
        }

    }

}
