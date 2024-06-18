package net.ritasister.wgrp.rslibs.updater;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UpdateNotify {

    private final JavaPlugin javaPlugin;
    private final WorldGuardRegionProtectBukkitPlugin worldGuardRegionProtectBukkitPlugin;
    static final String PLUGIN_URL_ADDRESS = "https://www.spigotmc.org/resources/81321";
    static final String NO_UPDATE_AVAILABLE = """
            <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
            <gold>               Current version: <aqua>%s
            <gold>             You are using the latest version.
            <yellow>=======================================""";

    public UpdateNotify(final JavaPlugin javaPlugin, final WorldGuardRegionProtectBukkitPlugin worldGuardRegionProtectBukkitPlugin) {
        this.javaPlugin = javaPlugin;
        this.worldGuardRegionProtectBukkitPlugin = worldGuardRegionProtectBukkitPlugin;
    }

    public void checkUpdateNotify(String oldVersion) {
        Server server = Bukkit.getServer();
        final String hasUpdate = """
                <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
                <gold>  There is a new version update available.
                <gold>             Current version: <dark_red>%s
                <gold>                New version: <aqua>%s
                <green>    Please, download new version here(Clickable)
                <green>    %s
                <yellow>=======================================""";
        new UpdateChecker(javaPlugin, 81321).getVersion(newVersion -> {
            if (oldVersion.equalsIgnoreCase(newVersion)) {
                worldGuardRegionProtectBukkitPlugin.messageToCommandSender(server.getConsoleSender(), String.format(
                        NO_UPDATE_AVAILABLE, newVersion));
            } else {
                worldGuardRegionProtectBukkitPlugin.messageToCommandSender(server.getConsoleSender(), String.format(
                        hasUpdate,
                        oldVersion,
                        newVersion,
                        PLUGIN_URL_ADDRESS));
            }
        });
    }

    public void checkUpdateNotify(String oldVersion, Player player, boolean checkUpdate, boolean sendNoUpdate) {
        final String hasUpdate = """
                <yellow>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<yellow>========
                <gold>  There is a new version update available.
                <gold>             Current version: <dark_red>%s
                <gold>                New version: <aqua>%s
                <green>    <u><click:open_url:'%s'><hover:show_text:'<gold>%s'>Please, download new version here(Clickable)</click></u></green>
                <yellow>=======================================""";
        if (checkUpdate) {
            new UpdateChecker(javaPlugin, 81321).getVersion(newVersion -> {
                if (oldVersion.equalsIgnoreCase(newVersion)) {
                    if(sendNoUpdate) {
                        worldGuardRegionProtectBukkitPlugin.messageToCommandSender(player, String.format(
                                NO_UPDATE_AVAILABLE,
                                newVersion
                        ));
                    }
                } else {
                    worldGuardRegionProtectBukkitPlugin.messageToCommandSender(player, String.format(
                            hasUpdate,
                            oldVersion,
                            newVersion,
                            PLUGIN_URL_ADDRESS,
                            PLUGIN_URL_ADDRESS
                    ));
                }
            });
        }
    }

}
