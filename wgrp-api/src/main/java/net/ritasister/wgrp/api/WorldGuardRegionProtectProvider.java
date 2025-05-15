package net.ritasister.wgrp.api;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serial;

import static org.jetbrains.annotations.ApiStatus.Internal;

/**
 * The WorldGuardRegionProtect API.
 *
 * <p>Provider to get the WorldGuardRegionProtect api instance.</p>
 */
public final class WorldGuardRegionProtectProvider {

    private static WorldGuardRegionProtect instance = null;

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
    static void register(final WorldGuardRegionProtect worldGuardRegionProtect) {
        WorldGuardRegionProtectProvider.instance = worldGuardRegionProtect;
    }

    @Internal
    static void unregister() {
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

        @Serial
        private static final long serialVersionUID = 1L;

        private static final String MESSAGE = """
                The WorldGuardRegionProtect API isn't loaded yet!
                This could be because:
                  a) the WorldGuardRegionProtect plugin is not installed or it failed to enable
                  b) the plugin in the stacktrace does not declare a dependency on WorldGuardRegionProtect
                  c) the plugin in the stacktrace is retrieving the API before the plugin 'enable' phase
                     (call the #get method in onEnable, not the constructor!)
                """;

        NotLoadedException() {
            super(MESSAGE);
        }

    }

}
