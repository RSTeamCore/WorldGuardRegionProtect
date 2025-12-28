package net.ritasister.wgrp.util.utility.platform;

import net.ritasister.wgrp.api.logging.PluginLogger;
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
                    logPlatformWarning(bootstrap, Platform.Type.BUKKIT);
                    return Platform.Type.BUKKIT.getPlatformName();
                } else {
                    setPlatformName(Platform.Type.SPIGOT.getPlatformName());
                    logPlatformWarning(bootstrap, Platform.Type.SPIGOT);
                    return Platform.Type.SPIGOT.getPlatformName();
                }
            }
            default -> {
                setPlatformName(Platform.Type.UNKNOWN.getPlatformName());
                logPlatformWarning(bootstrap, Platform.Type.UNKNOWN);
                return Platform.Type.UNKNOWN.getPlatformName();
            }
        }

        try {
            Class.forName(detectedClassName);
        } catch (ClassNotFoundException ignored) {}

        setPlatformName(detectedPlatform.getPlatformName());
        logPlatformWarning(bootstrap, detectedPlatform);
        return getPlatformName();
    }

    private void logPlatformWarning(@NotNull WorldGuardRegionProtectBootstrap bootstrap, @NotNull Platform.Type platformType) {
        final String version = bootstrap.getVersion();
        final boolean dev = version.contains("-SNAPSHOT") || version.contains("-dev");

        final String title = getTitle(platformType);
        final String description = getDescription(platformType);

        final String message =
            """
            ====================================================
        
                WorldGuardRegionProtect %s
                Server running on %s - %s
        
                %s
                %s
            
            ====================================================
            """.formatted(dev ? version : version + "-release", bootstrap.getServerVersion(), platformType.getPlatformName(), title, description);

        if (platformType == Platform.Type.UNKNOWN || platformType == Platform.Type.BUKKIT || platformType == Platform.Type.SPIGOT) {
            logger.warn(message);
        } else {
            logger.info(message);
        }
    }

    private static @NonNull String getTitle(Platform.@NonNull Type type) {
        return switch (type) {
            case UNKNOWN, BUKKIT, SPIGOT -> "Warning: Unsupported or legacy platform detected!";
            default -> "Platform compatibility check passed.";
        };
    }

    private static @NonNull String getDescription(Platform.@NonNull Type type) {
        return switch (type) {
            case UNKNOWN, BUKKIT, SPIGOT -> "It is recommended to use Paper, Folia or its forks for better performance and support!";
            default -> "Your setup is optimal for plugin performance and support.";
        };
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
