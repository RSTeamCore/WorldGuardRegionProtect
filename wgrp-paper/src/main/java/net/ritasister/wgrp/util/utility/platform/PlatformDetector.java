package net.ritasister.wgrp.util.utility.platform;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.platform.Platform;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NonNull;

public class PlatformDetector {

    private static String platformName;

    public String detectPlatform(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        final Platform.Type platformType = plugin.getType();
        final String detectedClassName;
        final Platform.Type detectedPlatform;

        switch (platformType) {
            case PAPER -> {
                detectedClassName = "com.destroystokyo.paper.ParticleBuilder";
                detectedPlatform = Platform.Type.PAPER;
            }
            case FOLIA -> {
                detectedClassName = "io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler";
                detectedPlatform = Platform.Type.FOLIA;
            }
            case BUKKIT -> {
                detectedClassName = "org.spigotmc.SpigotConfig";
                detectedPlatform = Platform.Type.BUKKIT;
            }
            case SPIGOT -> {
                if (isRunningOnBukkit()) {
                    setPlatformName(Platform.Type.BUKKIT.getPlatformName());
                    logPlatformWarning(plugin, Platform.Type.BUKKIT.getPlatformName());
                    return Platform.Type.BUKKIT.getPlatformName();
                } else {
                    setPlatformName(Platform.Type.SPIGOT.getPlatformName());
                    logPlatformWarning(plugin, Platform.Type.SPIGOT.getPlatformName());
                    return Platform.Type.SPIGOT.getPlatformName();
                }
            }
            default -> {
                setPlatformName(Platform.Type.UNKNOWN.getPlatformName());
                logPlatformWarning(plugin, Platform.Type.UNKNOWN.getPlatformName());
                return Platform.Type.UNKNOWN.getPlatformName();
            }
        }

        try {
            Class.forName(detectedClassName);
        } catch (ClassNotFoundException ignored) { }

        setPlatformName(detectedPlatform.getPlatformName());
        logPlatformWarning(plugin, detectedPlatform.getPlatformName());
        return getPlatformName();
    }

    private void logPlatformWarning(@NonNull WorldGuardRegionProtectPaperPlugin plugin, @NonNull String type) {
        final String pluginVersion = plugin.getWgrpPaperBase().getDescription().getVersion();
        final boolean isDevBuild = pluginVersion.contains("-SNAPSHOT") || pluginVersion.contains("-dev");
        final String pluginVersionModified = isDevBuild ? pluginVersion + "-dev" : pluginVersion;

        final String defaultMessage = """
                ====================================================
                
                    WorldGuardRegionProtect %s
                    Server running on %s - %s
                    %s
                    %s
                
                ====================================================
                """;

        final String minecraftVersion = Bukkit.getBukkitVersion().split("-")[0];

        final String isPaperForks = "Your setup is optimal for plugin performance and support.";
        final String isNotPaperForks = "It is recommended to use Paper, Folia or its forks for better performance and support!";
        final String isUnTrustedFork = """
                It is recommended to use Paper, Folia or its forks for better performance and support!
                Avoid using untrusted or unknown server forks.
                Support is not available for servers running on untrusted implementations.
                """;

        String messageDetail;
        if (type.equals(Platform.Type.UNKNOWN.getPlatformName())) {
            messageDetail = isUnTrustedFork;
            plugin.getLogger().warn(String.format(defaultMessage, pluginVersionModified, type, minecraftVersion, messageDetail, ""));
        } else if (type.equals(Platform.Type.BUKKIT.getPlatformName()) || type.equals(Platform.Type.SPIGOT.getPlatformName())) {
            messageDetail = isNotPaperForks;
            plugin.getLogger().warn(String.format(defaultMessage, pluginVersionModified, type, minecraftVersion, messageDetail, ""));
        } else {
            messageDetail = isPaperForks;
            plugin.getLogger().info(String.format(defaultMessage, pluginVersionModified, type, minecraftVersion, messageDetail, ""));
        }
    }

    private boolean isRunningOnBukkit() {
        return Bukkit.getServer().getName().contains("Bukkit") || Bukkit.getServer().getName().contains("CraftBukkit");
    }

    private static void setPlatformName(String name) {
        platformName = name;
    }

    public static String getPlatformName() {
        return platformName;
    }
}
