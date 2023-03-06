package net.ritasister.wgrp.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

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

    public void notifyAboutBuild(@NotNull WGRPContainer wgrpContainer) {
        String pluginVersion = wgrpContainer.getPluginVersion();
        Container container = wgrpContainer.getMessages();
        if (pluginVersion.contains("alpha")
                || pluginVersion.contains("beta")
                || pluginVersion.contains("pre")) {
            Bukkit.getLogger().warning("""
                     This is a test build. This building may be unstable!
                     When reporting a bug:
                     Use the issue tracker! Don't report bugs in the reviews.
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                     Provide as much information as possible.
                     Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                     Provide any stack traces or "errors" using pastebin.
                    """);
        } else {
            Bukkit.getLogger().info("This is the latest stable building.");
        }
        Bukkit.getConsoleSender().sendMessage(String.format(""" 
                Using %s language version %s. Author of this localization - %s.
                """, container.get("langTitle.language"), container.get("langTitle.version"), container.get("langTitle.author")
        ));
    }

}
