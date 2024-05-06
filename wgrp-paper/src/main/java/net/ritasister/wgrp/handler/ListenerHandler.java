package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.listener.BlockProtect;
import net.ritasister.wgrp.listener.EntityProtect;
import net.ritasister.wgrp.listener.HangingProtect;
import net.ritasister.wgrp.listener.MiscProtect;
import net.ritasister.wgrp.listener.PlayerProtect;
import net.ritasister.wgrp.listener.ToolsProtect;
import net.ritasister.wgrp.listener.VehicleProtect;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ListenerHandler implements Handler<PluginManager> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public ListenerHandler(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public void handle(final @NotNull PluginManager pluginManager) {
        List<Listener> allListeners = List.of(
                new BlockProtect(wgrpBukkitPlugin),
                new EntityProtect(wgrpBukkitPlugin),
                new HangingProtect(wgrpBukkitPlugin),
                new MiscProtect(wgrpBukkitPlugin),
                new PlayerProtect(wgrpBukkitPlugin),
                new ToolsProtect(wgrpBukkitPlugin),
                new VehicleProtect(wgrpBukkitPlugin));

        allListeners.forEach((listener) -> pluginManager.registerEvents(listener, wgrpBukkitPlugin.getWgrpBukkitBase()));

        wgrpBukkitPlugin.getPluginLogger().info("All listeners registered successfully!");
    }

}
