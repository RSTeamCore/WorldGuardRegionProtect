package net.ritasister.wgrp.core.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.api.RegionAdapter;
import net.ritasister.wgrp.api.WorldGuardRegionMetadata;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public abstract class WorldGuardRegionProtectProvider implements WorldGuardRegionProtect {

    private static WorldGuardRegionProtect instance;
    private static WorldGuardRegionMetadata metadata;

    private WorldGuardRegionProtectProvider() {
        throw new IllegalArgumentException();
    }

    @ApiStatus.Internal
    public static void setWorldGuardRegionProtect(final @NotNull WorldGuardRegionProtect instance) {
        metadata = instance.getWorldGuardMetadata();
        if (WorldGuardRegionProtectProvider.instance != null) {
            throw new IllegalArgumentException("WorldGuardRegionProtectProvider is already set!");
        }
        WorldGuardRegionProtectProvider.instance = instance;
    }

    public static @NotNull WorldGuardRegionProtect get() {
        return instance;
    }

    @Override
    public <T, P, R> RegionAdapter<T, P, R> getRegionAdapter() {
        return null;
    }

    @Override
    public WorldGuardRegionMetadata getWorldGuardMetadata() {
        return metadata;
    }

    public void messageToCommandSender(@NotNull CommandSender commandSender, String message) {
        var miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(String.valueOf(parsed));
    }

}
