package net.ritasister.register;

import net.ritasister.listener.protect.RegionProtect;
import net.ritasister.rslibs.api.interfaces.IRegisterListener;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class RegisterListener implements IRegisterListener {

	@Override
	public void registerListener(@NotNull PluginManager pluginManager) {
		//RegionProtect
		final RegionProtect regionProtect = new RegionProtect(WorldGuardRegionProtect.getInstance());
		pluginManager.registerEvents(regionProtect, WorldGuardRegionProtect.getPlugin(WorldGuardRegionProtect.class));

		WorldGuardRegionProtect.getInstance().rsApi.getLogger().info("&2All listeners registered successfully!");
	}
}