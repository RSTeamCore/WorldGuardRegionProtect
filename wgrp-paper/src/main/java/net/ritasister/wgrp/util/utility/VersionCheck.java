package net.ritasister.wgrp.util.utility;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.Bukkit;

import java.util.Set;

public class VersionCheck {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public VersionCheck(final WorldGuardRegionProtectPaperPlugin plugin) {
        this.wgrpPlugin = plugin;
    }

    private final static String SUPPORTED_VERSION_RANGE = "1.20 - 1.21.5";
    private final static Set<String> SUPPORTED_VERSION = Set.of(
            "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6",
            "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5");

    /**
     * Initializes all used NMS classes, constructor fields, and methods.
     * Returns {@code true} if everything went successfully and version marked as compatible,
     * {@code false} if anything went wrong or version not marked as compatible.
     *
     * @return {@code true} if server version compatible, {@code false} if not
     */
    public boolean isVersionSupported() {
        final String minecraftVersion = Bukkit.getBukkitVersion().split("-")[0];
        final long start = System.currentTimeMillis();
        try {
            if (VersionCheck.SUPPORTED_VERSION.contains(minecraftVersion)) {
                logVersionSupport(start);
                return true;
            } else {
                logUnsupportedVersion(minecraftVersion);
            }
        } catch (Exception e) {
            handleCompatibilityError(minecraftVersion);
        }
        return false;
    }

    private void logVersionSupport(long start) {
        wgrpPlugin.getLogger().info("Loaded NMS hook in " + (System.currentTimeMillis() - start) + "ms");
        wgrpPlugin.getLogger().info("Current supported versions range: " + VersionCheck.SUPPORTED_VERSION_RANGE);
    }

    private void logUnsupportedVersion(String minecraftVersion) {
        wgrpPlugin.getLogger().info(
                "No compatibility issue was found, but this plugin version does not claim to support your server package (" + minecraftVersion + ").");
    }

    private void handleCompatibilityError(String minecraftVersion) {
        if (VersionCheck.SUPPORTED_VERSION.contains(minecraftVersion)) {
            wgrpPlugin.getLogger().severe(
                    "Your server version is marked as compatible, but a compatibility issue was found. Please report the error (include your server version & fork).");
        } else {
            wgrpPlugin.getLogger().severe(
                    "Your server version is completely unsupported. This plugin version only supports " + VersionCheck.SUPPORTED_VERSION_RANGE + ". Disabling.");
        }
    }

    public static String getSupportedVersionRange() {
        return SUPPORTED_VERSION_RANGE;
    }
}
