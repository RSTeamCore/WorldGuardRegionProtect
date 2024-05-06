package wgrp.loader;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitBase;
import wgrp.rslibs.api.RSApiImpl;

public class WGRPChecker {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;

    private boolean isPaper;

    @Contract(pure = true)
    public WGRPChecker(final @NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        this.wgrpBukkitBase = wgrpBukkitBase;
    }

    public void checkStartUpVersionServer() {
        if (!RSApiImpl.isVersionSupported()) {
            Bukkit.getLogger().severe("""
                    This plugin version works only on 1.20+!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version of plugin for your server version.
                    """);
            Bukkit.getServer().getPluginManager().disablePlugin(wgrpBukkitBase);
        }
    }

    public void checkIfRunningOnPaper() {
        isPaper = false;
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isPaper = true;
        } catch (ClassNotFoundException ignored) {
            Bukkit.getLogger().info(String.format("""
                            Using paper: %s
                            Better if you are running your server on paper or other forks of paper.
                            Please don't use any untrusted forks.
                            """, isPaper()));
        }
        Bukkit.getLogger().info(String.format("Using paper: %s", isPaper()));
    }

    public void notifyAboutBuild() {
        if (wgrpBukkitBase.getDescription().getVersion().contains("alpha")
                || wgrpBukkitBase.getDescription().getVersion().contains("beta")
                || wgrpBukkitBase.getDescription().getVersion().contains("pre")) {
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
        Bukkit.getLogger().info(String.format(
                """ 
                Using %s language version %s. Author of this localization - %s.
                """,
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.language"),
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.version"),
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.author")
        ));
    }

}
