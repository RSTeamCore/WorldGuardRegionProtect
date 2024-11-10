package net.ritasister.wgrp.api;

import org.checkerframework.checker.nullness.qual.NonNull;

import static org.jetbrains.annotations.ApiStatus.Internal;

/**
 * The WorldGuardRegionProtect API.
 *
 * <p>Provider to get the WorldGuardRegionProtect api instance.</p>
 */
public final class WorldGuardRegionProtectProvider {

    private static WorldGuardRegionProtect instance;

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

    @Internal
    static void setWorldGuardRegionProtect(final WorldGuardRegionProtect worldGuardRegionProtect) {
        WorldGuardRegionProtectProvider.instance = worldGuardRegionProtect;
    }

    @Internal
    static void unSetWorldGuardRegionProtect() {
        WorldGuardRegionProtectProvider.instance = null;
    }

    @Internal
    private WorldGuardRegionProtectProvider() {
        throw new UnsupportedOperationException("This class cannot be instantiated.");
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
