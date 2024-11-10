package net.ritasister.wgrp;

import org.bukkit.plugin.java.JavaPlugin;

public final class WorldGuardRegionProtectPaperBase extends JavaPlugin {

    private WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin;

    @Override
    public void onEnable() {
        wgrpPaperPlugin = new WorldGuardRegionProtectPaperPlugin(this);
    }

    @Override
    public void onDisable() {
        wgrpPaperPlugin.unLoad();
    }

}
