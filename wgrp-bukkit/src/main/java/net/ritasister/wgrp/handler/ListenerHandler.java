package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.listener.BlockProtect;
import net.ritasister.wgrp.listener.EntityProtect;
import net.ritasister.wgrp.listener.HangingProtect;
import net.ritasister.wgrp.listener.MiscProtect;
import net.ritasister.wgrp.listener.PlayerProtect;
import net.ritasister.wgrp.listener.ToolsProtect;
import net.ritasister.wgrp.listener.VehicleProtect;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class ListenerHandler extends AbstractHandler<PluginManager> {

    private final WorldGuardRegionProtect wgRegionProtect;

    public ListenerHandler(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect = wgRegionProtect;
    }

    @Override
    public void handle(final @NotNull PluginManager pluginManager) {
        final BlockProtect blockProtect = new BlockProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(blockProtect, wgRegionProtect.getWGRPBukkitPlugin());

        final EntityProtect entityProtect = new EntityProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(entityProtect, wgRegionProtect.getWGRPBukkitPlugin());

        final HangingProtect hangingProtect = new HangingProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(hangingProtect, wgRegionProtect.getWGRPBukkitPlugin());

        final MiscProtect miscProtect = new MiscProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(miscProtect, wgRegionProtect.getWGRPBukkitPlugin());

        final PlayerProtect playerProtect = new PlayerProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(playerProtect, wgRegionProtect.getWGRPBukkitPlugin());

        final ToolsProtect toolsProtect = new ToolsProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(toolsProtect, wgRegionProtect.getWGRPBukkitPlugin());

        final VehicleProtect vehicleProtect = new VehicleProtect(wgRegionProtect.getWgrpContainer());
        pluginManager.registerEvents(vehicleProtect, wgRegionProtect.getWGRPBukkitPlugin());


        WGRPContainer.getLogger().info("All listeners registered successfully!");
    }

}
