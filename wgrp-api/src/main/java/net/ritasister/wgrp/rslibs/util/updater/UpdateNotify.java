package net.ritasister.wgrp.rslibs.util.updater;

import io.papermc.paper.plugin.configuration.PluginMeta;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtect;
import net.rsteamcore.config.update.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UpdateNotify {

    private final JavaPlugin javaPlugin;
    private final WorldGuardRegionProtect worldGuardRegionProtect;

    public UpdateNotify(final JavaPlugin javaPlugin, final WorldGuardRegionProtect worldGuardRegionProtect) {
        this.javaPlugin = javaPlugin;
        this.worldGuardRegionProtect = worldGuardRegionProtect;
    }

    public void checkUpdateNotify(PluginMeta pluginMeta) {
        final String noUpdate = """
                ========[WorldGuardRegionProtect]========
                          Current version: %s
                       You are using the latest version.
                =======================================""";
        final String hasUpdate = """
                ========[WorldGuardRegionProtect]========
                 There is a new version update available.
                        Current version: %s
                          New version: %s
                   Please, download new version here
                 https://www.spigotmc.org/resources/81321/
                =======================================""";
        new UpdateChecker(javaPlugin, 81321).getVersion(version -> {
            if (pluginMeta.getVersion().equalsIgnoreCase(version)) {
                Bukkit.getConsoleSender().sendMessage(String.format(noUpdate, version));
            } else {
                Bukkit.getConsoleSender().sendMessage(String.format(hasUpdate, pluginMeta.getVersion(), version));
            }
        });
    }

    public void checkUpdateNotify(PluginMeta pluginMeta, Player player, boolean checkUpdate, boolean sendNoUpdate) {
        final String noUpdate = """
               <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
                              <gold>Current version: <aqua>%s
                            <gold>You are using the latest version.
               <yellow>=======================================""";
        final String hasUpdate = """
                <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
                <gold> There is a new version update available.
                <red>        Current version: <dark_red>%s
                <dark_aqua>          New version: <aqua>%s
                <gold>   Please, download new version here
                <gold> https://www.spigotmc.org/resources/81321/
                <yellow>=======================================""";
        if (checkUpdate) {
            new UpdateChecker(javaPlugin, 81321).getVersion(version -> {
                if (pluginMeta.getVersion().equalsIgnoreCase(version)) {
                    if(sendNoUpdate) {
                        worldGuardRegionProtect.messageToCommandSender(player, String.format(noUpdate, version));
                    }
                } else {
                    worldGuardRegionProtect.messageToCommandSender(player, String.format(hasUpdate, pluginMeta.getVersion(), version));
                }
            });
        }
    }

}
