package net.ritasister.wgrp.util.utility.version;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.version.VersionChecker;
import net.ritasister.wgrp.api.config.version.VersionProvider;

import java.util.Set;

public class MinecraftVersionChecker implements VersionChecker<WorldGuardRegionProtectPaperPlugin> {

    private static final String SUPPORTED_VERSION_RANGE = "1.20 - 1.21.11";
    private static final Set<String> SUPPORTED_VERSIONS = Set.of(
            "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6",
            "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11"
    );

    private final VersionProvider versionProvider;

    public MinecraftVersionChecker(VersionProvider versionProvider) {
        this.versionProvider = versionProvider;
    }

    @Override
    public boolean check(WorldGuardRegionProtectPaperPlugin plugin) {
        final String currentVersion = getCurrentVersion();
        if (isVersionSupported()) {
            plugin.getLogger().info("Server version supported: " + currentVersion);
            plugin.getLogger().info("Supported range: " + SUPPORTED_VERSION_RANGE);
            return true;
        } else {
            plugin.getLogger().severe(String.format("""
                    ====================================================
                    
                        Your server version is %s.
                        WorldGuardRegionProtect only works on %s!
                        Please refer to this thread: https://www.spigotmc.org/resources/81321/
                    
                    ====================================================
                    """, currentVersion, SUPPORTED_VERSION_RANGE));
            return false;
        }
    }

    public boolean isVersionSupported() {
        return SUPPORTED_VERSIONS.contains(getCurrentVersion());
    }

    public String getCurrentVersion() {
        return versionProvider.getServerVersion();
    }

    public static String getSupportedVersionRange() {
        return SUPPORTED_VERSION_RANGE;
    }

    public static Set<String> getSupportedVersions() {
        return SUPPORTED_VERSIONS;
    }
}
