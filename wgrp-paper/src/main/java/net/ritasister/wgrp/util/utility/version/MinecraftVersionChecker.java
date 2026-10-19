package net.ritasister.wgrp.util.utility.version;

import net.ritasister.wgrp.WGRPBootstrap;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.jetbrains.annotations.Contract;

import java.util.Set;

public class MinecraftVersionChecker {

    private static final String MIN_SUPPORTED = "26.1";
    private static final String MAX_SUPPORTED = "26.3";

    private static final String SUPPORTED_VERSION_RANGE = MIN_SUPPORTED + " - " + MAX_SUPPORTED;
    private static final Set<String> SUPPORTED_VERSIONS = Set.of(
            "26.1", "26.1.1", "26.1.2", "26.2", "26.3"
    );

    private final WGRPBootstrap bootstrap;

    public MinecraftVersionChecker(WGRPBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public boolean check(WorldGuardRegionProtectPaperPlugin plugin) {
        final String currentVersion = getCurrentVersion();
        if (isVersionSupported()) {
            plugin.getLogger().info("Server version supported: " + currentVersion);
            plugin.getLogger().info("Supported range: " + SUPPORTED_VERSION_RANGE);
            return true;
        } else {
            plugin.getLogger().severe(String.format(
                    """
                            ====================================================
                            
                                Your server version is %s.
                                WorldGuardRegionProtect only works on %s!
                                Please refer to this thread: https://www.spigotmc.org/resources/81321/
                            
                            ====================================================
                            """, currentVersion, SUPPORTED_VERSION_RANGE
            ));
            return false;
        }
    }

    public boolean isVersionSupported() {
        try {
            final Version current = new Version(getCurrentVersion());
            final Version min = new Version(MIN_SUPPORTED);
            final Version max = new Version(MAX_SUPPORTED);

            return current.compareTo(min) >= 0 && current.compareTo(max) <= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentVersion() {
        return bootstrap.getMinecraftVersion();
    }

    public static String getSupportedVersionRange() {
        return SUPPORTED_VERSION_RANGE;
    }

    /**
     * Retrieves the set of supported versions.
     * <p>
     * This method is marked as deprecated and is planned for removal in a future version.
     *
     * @return a set containing the supported version strings
     */
    @Contract(pure = true)
    @Deprecated(forRemoval = true, since = "26.2")
    public static Set<String> getSupportedVersions() {
        return SUPPORTED_VERSIONS;
    }
}
