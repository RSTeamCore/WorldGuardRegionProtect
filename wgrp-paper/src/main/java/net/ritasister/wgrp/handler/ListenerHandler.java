package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.listener.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Handler for register listeners.
 */
public class ListenerHandler implements Handler<PluginManager> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public ListenerHandler(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public void handle(final @NotNull PluginManager pluginManager) {
        final List<Listener> allListeners = List.of(
                new AdminProtect(wgrpPlugin),
                new BlockProtect(wgrpPlugin),
                new EntityProtect(wgrpPlugin),
                new HangingProtect(wgrpPlugin),
                new MiscProtect(wgrpPlugin),
                new PlayerProtect(wgrpPlugin),
                new ToolsProtect(wgrpPlugin),
                new VehicleProtect(wgrpPlugin));

        allListeners.forEach(listener -> pluginManager.registerEvents(listener, wgrpPlugin.getWgrpPaperBase()));

        wgrpPlugin.getLogger().info("All listeners registered successfully!");
    }

}
