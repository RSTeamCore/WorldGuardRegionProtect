package net.ritasister.wgrp;

import org.bukkit.plugin.java.JavaPlugin;

public final class WorldGuardRegionProtectPaperBase extends JavaPlugin {

    private final WorldGuardRegionProtectPaperPlugin plugin;

    public WorldGuardRegionProtectPaperBase() {
        this.plugin = new WorldGuardRegionProtectPaperPlugin(this);
    }

    @Override
    public void onEnable() {
        this.plugin.onEnable();
    }

    @Override
    public void onDisable() {
        this.plugin.onDisable();
    }

}
