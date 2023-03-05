package net.ritasister.wgrp.rslibs.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Provides static access to the {@link WorldGuardRegionProtectApi} API.
 *
 * <p>Ideally, the ServiceManager for the platform should be used to obtain an
 * instance, however, this provider can be used if this is not viable.</p>
 */
public abstract class WorldGuardRegionProtectApi {

    private static WorldGuardRegionProtectApi instance;


    public WorldGuardRegionProtectApi() {
        instance = this;
    }

    /**
     * Instance setter for internal use by the plugin only.
     *
     * @param instance API instance
     */
    @ApiStatus.Internal
    protected static void setInstance(WorldGuardRegionProtectApi instance) {
        WorldGuardRegionProtectApi.instance = instance;
    }

    /**
     * Returns API instance. If instance was not set by the plugin, throws
     * {@code IllegalStateException}. This is usually caused by shading the API
     * into own project, which is not allowed. Another option is calling the method
     * before plugin was able to load.
     *
     * @return API instance
     * @throws IllegalStateException If instance is {@code null}
     */
    public static @NonNull WorldGuardRegionProtectApi getInstance() {
        if (instance == null) {
            throw new IllegalStateException("API instance is null. This likely means you shaded WGRP's API into your project" +
                    " instead of only using it, which is not allowed.");
        }
        return instance;
    }

    public static void messageToCommandSender(@NotNull CommandSender commandSender, String message) {
        var miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(parsed);
    }

    public void messageToConsoleSender(@NotNull ConsoleCommandSender sender, String message) {
        var miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);
        sender.sendMessage(parsed);
    }
}
