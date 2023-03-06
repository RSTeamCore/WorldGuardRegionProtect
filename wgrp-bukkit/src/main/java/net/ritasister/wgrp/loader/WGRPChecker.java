package net.ritasister.wgrp.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WGRPChecker {

    private final WorldGuardRegionProtect worldGuardRegionProtect;

    void checkStartUpVersionServer() {
        if (!worldGuardRegionProtect.getWgrpContainer().getRsApi().isVersionSupported()) {
            Bukkit.getLogger().severe("""
                    This plugin version works only on 1.19+!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version for your server version.
                    """);
        }
    }

    void checkForUsePermissionsEx() {
        if(!worldGuardRegionProtect.getWgrpContainer().getPluginManager().isPluginEnabled("PermissionsEx")) {
            Bukkit.getLogger().severe("""
                    Wea are not supported old version of permissions plugin like PermissionsEx, please use LuckPerms or another.
                    """);
            worldGuardRegionProtect.getWGRPBukkitPlugin().onDisable();
        }
    }

}
