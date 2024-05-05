package wgrp.handler;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.listener.BlockProtect;
import wgrp.listener.EntityProtect;
import wgrp.listener.HangingProtect;
import wgrp.listener.MiscProtect;
import wgrp.listener.PlayerProtect;
import wgrp.listener.ToolsProtect;
import wgrp.listener.VehicleProtect;

import java.util.List;

@Slf4j
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

        log.info("All listeners registered successfully!");
    }

}
