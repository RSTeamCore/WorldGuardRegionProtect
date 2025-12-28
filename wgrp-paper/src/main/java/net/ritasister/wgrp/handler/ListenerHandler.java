package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.listener.player.AdminProtect;
import net.ritasister.wgrp.listener.miscellaneous.BlockProtect;
import net.ritasister.wgrp.listener.entity.EntityProtect;
import net.ritasister.wgrp.listener.entity.HangingProtect;
import net.ritasister.wgrp.listener.miscellaneous.MiscProtect;
import net.ritasister.wgrp.listener.player.PlayerProtect;
import net.ritasister.wgrp.listener.miscellaneous.ToolsProtect;
import net.ritasister.wgrp.listener.entity.VehicleProtect;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

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

        allListeners.forEach(listener -> {
            this.wgrpPlugin.getLogger().info("Registered listener: " + listener.getClass().getSimpleName());
            this.wgrpPlugin.getListenerHandlerMap().put(listener.getClass(), listener);
            pluginManager.registerEvents(listener, this.wgrpPlugin.getBootstrap().getLoader());
        });

        this.wgrpPlugin.getLogger().info("Finished registering listeners.");
        this.wgrpPlugin.getLogger().info(String.format("All listeners registered successfully! List of Listeners: %s",
                allListeners.stream()
                        .map(listener -> listener.getClass().getSimpleName())
                        .collect(Collectors.joining(", "))));
    }

}
