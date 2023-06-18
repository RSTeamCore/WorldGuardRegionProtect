package net.ritasister.wgrp;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtect;
import org.bukkit.plugin.java.JavaPlugin;

@Slf4j
public final class WorldGuardRegionProtectBukkitBase extends JavaPlugin implements WorldGuardRegionProtectBase {

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
