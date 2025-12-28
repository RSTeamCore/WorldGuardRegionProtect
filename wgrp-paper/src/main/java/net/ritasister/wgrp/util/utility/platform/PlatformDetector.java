package net.ritasister.wgrp.util.utility.platform;

import net.ritasister.wgrp.WGRPBootstrap;
import net.ritasister.wgrp.api.logging.PluginLogger;
import net.ritasister.wgrp.api.metadata.WorldGuardRegionProtectMetadata;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.plugin.WorldGuardRegionProtectBootstrap;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class PlatformDetector {

    private static String platformName;
    private final PluginLogger logger;

    public PlatformDetector(@NotNull PluginLogger logger) {
        this.logger = logger;
    }

    public String detectPlatform(@NotNull WorldGuardRegionProtectBootstrap bootstrap, Platform.@NotNull Type platformType) {
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
                    logPlatformWarning(bootstrap, Platform.Type.BUKKIT.getPlatformName());
                    return Platform.Type.BUKKIT.getPlatformName();
                } else {
                    setPlatformName(Platform.Type.SPIGOT.getPlatformName());
                    logPlatformWarning(bootstrap, Platform.Type.SPIGOT.getPlatformName());
                    return Platform.Type.SPIGOT.getPlatformName();
                }
            }
            default -> {
                setPlatformName(Platform.Type.UNKNOWN.getPlatformName());
                logPlatformWarning(bootstrap, Platform.Type.UNKNOWN.getPlatformName());
                return Platform.Type.UNKNOWN.getPlatformName();
            }
        }

        try {
            Class.forName(detectedClassName);
        } catch (ClassNotFoundException ignored) {}

        setPlatformName(detectedPlatform.getPlatformName());
        logPlatformWarning(bootstrap, detectedPlatform.getPlatformName());
        return getPlatformName();
    }

    private void logPlatformWarning(@NotNull WorldGuardRegionProtectBootstrap bootstrap, @NotNull String type) {
        final String pluginVersion = bootstrap.getVersion();
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

        final String format = getString(type, pluginVersionModified);
        if (type.equals(Platform.Type.UNKNOWN.getPlatformName()) ||
                type.equals(Platform.Type.BUKKIT.getPlatformName()) ||
                type.equals(Platform.Type.SPIGOT.getPlatformName())) {
            logger.warn(format);
        } else {
            logger.info(format);
        }
    }

    private static @NonNull String getString(@NonNull String type, String pluginVersionModified) {
        final String minecraftVersion = Bukkit.getBukkitVersion().split("-")[0];

        final String messageDetail = getString(type);

        return String.format("====================================================\n\n    WorldGuardRegionProtect %s\n    Server running on %s - %s\n    %s\n    %s\n\n====================================================\n", pluginVersionModified, type, minecraftVersion, messageDetail, "");
    }

    private static @NonNull String getString(@NonNull String type) {
        final String messageDetail;
        if (type.equals(Platform.Type.UNKNOWN.getPlatformName())) {
            messageDetail = "It is recommended to use Paper, Folia or its forks for better performance and support!";
        } else if (type.equals(Platform.Type.BUKKIT.getPlatformName()) || type.equals(Platform.Type.SPIGOT.getPlatformName())) {
            messageDetail = "It is recommended to use Paper, Folia or its forks for better performance and support!";
        } else {
            messageDetail = "Your setup is optimal for plugin performance and support.";
        }
        return messageDetail;
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
