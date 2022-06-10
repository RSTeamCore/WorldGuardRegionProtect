package net.ritasister.wgrp.handler;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.listener.protect.RegionProtect;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

@Singleton
public final class ListenerHandler {
	@Inject
	private WorldGuardRegionProtect wgRegionProtect;

	public void listenerHandler(@NotNull PluginManager pluginManager) {
		final RegionProtect regionProtect = new RegionProtect();
		pluginManager.registerEvents(regionProtect, wgRegionProtect.getWgrpBukkitPlugin());

		wgRegionProtect.getRsApi().getLogger().info("&2All listeners registered successfully!");
	}
}