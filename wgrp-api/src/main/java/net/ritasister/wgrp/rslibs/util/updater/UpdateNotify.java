package net.ritasister.wgrp.rslibs.util.updater;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.plugin.configuration.PluginMeta;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.rslibs.api.WorldGuardRegionProtectApi;
import net.rsteamcore.config.update.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class UpdateNotify {

    private final JavaPlugin javaPlugin;

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

    public void checkUpdateNotify(PluginMeta pluginMeta, Player player, boolean checkUpdate) {
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
                    WorldGuardRegionProtectApi.messageToCommandSender(player, String.format(noUpdate, version));
                } else {
                    WorldGuardRegionProtectApi.messageToCommandSender(player, String.format(hasUpdate, pluginMeta.getVersion(), version));
                }
            });
        }
    }

}
