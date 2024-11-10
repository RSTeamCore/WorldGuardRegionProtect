package net.ritasister.wgrp;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.api.WorldGuardRegionProtectProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldGuardRegionProtectPaperBase extends JavaPlugin implements WorldGuardRegionProtectBase {

    private WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin;

    @Override
    public void onEnable() {
        wgrpPaperPlugin = new WorldGuardRegionProtectPaperPlugin(this);
    }

    @Override
    public void onDisable() {
        wgrpPaperPlugin.unLoad();
    }

    @Override
    public WorldGuardRegionProtect getApi() {
        return (WorldGuardRegionProtect) this.wgrpPaperPlugin;
    }

}
