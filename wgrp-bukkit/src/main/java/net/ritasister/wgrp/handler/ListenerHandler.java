package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.rslibs.api.interfaces.IListenerHandler;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.listener.protect.RegionProtect;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public record ListenerHandler(WorldGuardRegionProtect wgRegionProtect) implements IListenerHandler {

	@Override
	public void listenerHandler(@NotNull PluginManager pluginManager) {
		//RegionProtect
		final RegionProtect regionProtect = new RegionProtect(wgRegionProtect);
		pluginManager.registerEvents(regionProtect, wgRegionProtect.getWgrpBukkitPlugin());

		wgRegionProtect.getRsApi().getLogger().info("&2All listeners registered successfully!");
	}
}