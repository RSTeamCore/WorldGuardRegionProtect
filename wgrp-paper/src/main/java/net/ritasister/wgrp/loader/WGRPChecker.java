package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class WGRPChecker {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private boolean isPaper;

    @Contract(pure = true)
    public WGRPChecker(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    public void checkStartUpVersionServer() {
        if (!wgrpBukkitPlugin.getRsApi().isVersionSupported()) {
            wgrpBukkitPlugin.getPluginLogger().severe(String.format("""
                    This plugin version works only on %s!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version of plugin for your server version.
                    """, RSApiImpl.SUPPORTED_VERSION_RANGE));
            Bukkit.getServer().getPluginManager().disablePlugin(wgrpBukkitPlugin.getWgrpBukkitBase());
        }
    }

    public void checkIfRunningOnPaper() {
        isPaper = false;
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isPaper = true;
        } catch (ClassNotFoundException ignored) {
            wgrpBukkitPlugin.getPluginLogger().info(String.format("""
                            Using paper: %s
                            Better if you are running your server on paper or other forks of paper.
                            Please don't use any untrusted forks.
                            """, isPaper()));
        }
        wgrpBukkitPlugin.getPluginLogger().info(String.format("Using paper: %s", isPaper()));
    }

    public void notifyAboutBuild() {
        if (wgrpBukkitPlugin.getWgrpBukkitBase().getPluginMeta().getVersion().contains("alpha")
                || wgrpBukkitPlugin.getWgrpBukkitBase().getPluginMeta().getVersion().contains("beta")
                || wgrpBukkitPlugin.getWgrpBukkitBase().getPluginMeta().getVersion().contains("pre")) {
            wgrpBukkitPlugin.getPluginLogger().warn("""
                     This is a test build. This building may be unstable!
                     When reporting a bug:
                     Use the issue tracker! Don't report bugs in the reviews.
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                     Provide as much information as possible.
                     Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                     Provide any stack traces or "errors" using pastebin.
                    """);
        } else {
            wgrpBukkitPlugin.getPluginLogger().info("This is the latest stable building.");
        }
        wgrpBukkitPlugin.getPluginLogger().info(String.format(
                """ 
                Using %s language version %s. Author of this localization - %s.
                """,
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.language"),
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.version"),
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.author")
        ));
    }

    public boolean isPaper() {
        return this.isPaper;
    }

}
