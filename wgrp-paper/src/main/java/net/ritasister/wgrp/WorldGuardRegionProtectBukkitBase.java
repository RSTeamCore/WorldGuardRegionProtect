package net.ritasister.wgrp;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.core.WorldGuardRegionProtectBase;
import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class WorldGuardRegionProtectBukkitBase extends JavaPlugin implements WorldGuardRegionProtectBase {

    private WorldGuardRegionProtectPlugin worldGuardRegionProtect;

    @Override
    public void onEnable() {
        worldGuardRegionProtect = new WorldGuardRegionProtectBukkitPlugin(this);
    }

    @Override
    public void onDisable() {
        worldGuardRegionProtect.disable();
    }

    @Override
    public WorldGuardRegionProtect getApi() {
        return worldGuardRegionProtect;
    }

}
