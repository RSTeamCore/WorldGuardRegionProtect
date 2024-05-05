package net.ritasister.wgrp.core;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.core.api.WorldGuardRegionProtectProvider;
import net.ritasister.wgrp.core.util.ServerType;
import net.ritasister.wgrp.core.util.Version;
import net.ritasister.wgrp.core.util.config.loader.ConfigLoader;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

@Getter
public abstract class WorldGuardRegionProtectPlugin implements WorldGuardRegionProtect {

    protected final Version version;
    private final ServerType serverType;
    private final ConfigLoader configLoader = new ConfigLoader();

    protected WorldGuardRegionProtectPlugin(final String version, final ServerType serverType) {
        this.version = new Version(version);
        this.serverType = serverType;
        Bukkit.getLogger().info("You are running on {}" + serverType);
        WorldGuardRegionProtectProvider.setWorldGuardRegionProtect(this);
    }

    public void disable() {
    }

    public void messageToCommandSender(@NotNull CommandSender commandSender, String message) {
        var miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(String.valueOf(parsed));
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

}
