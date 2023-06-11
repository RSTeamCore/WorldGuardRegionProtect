package net.ritasister.wgrp.handler;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtect;
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

@Slf4j
public class ListenerHandler implements Handler<PluginManager> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public ListenerHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect = wgRegionProtect;
    }

    @Override
    public void handle(final @NotNull PluginManager pluginManager) {
        List<Listener> allListeners = List.of(
                new BlockProtect(wgRegionProtect.getWgrpContainer()),
                new EntityProtect(wgRegionProtect.getWgrpContainer()),
                new HangingProtect(wgRegionProtect.getWgrpContainer()),
                new MiscProtect(wgRegionProtect.getWgrpContainer()),
                new PlayerProtect(wgRegionProtect.getWgrpContainer()),
                new ToolsProtect(wgRegionProtect.getWgrpContainer()),
                new VehicleProtect(wgRegionProtect.getWgrpContainer()));

        allListeners.forEach((listener) -> pluginManager.registerEvents(listener, wgRegionProtect.getWGRPBukkitPlugin()));

        log.info("All listeners registered successfully!");
    }

}
