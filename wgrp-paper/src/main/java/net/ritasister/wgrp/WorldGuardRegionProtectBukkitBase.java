package net.ritasister.wgrp;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.core.WorldGuardRegionProtectBase;
import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class WorldGuardRegionProtectBukkitBase extends JavaPlugin implements WorldGuardRegionProtectBase {

    private WorldGuardRegionProtectPlugin wgrpPlugin;

    @Override
    public void onEnable() {
        wgrpPlugin = new WorldGuardRegionProtectBukkitPlugin(this);
    }

    @Override
    public void onDisable() {
        wgrpPlugin.disable();
    }

    @Override
    public WorldGuardRegionProtect getApi() {
        return wgrpPlugin;
    }

}
