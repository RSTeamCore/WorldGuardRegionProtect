package wgrp.loader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitBase;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.api.RSApi;

@Slf4j
public class WGRPChecker {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;

    @Getter
    private boolean isPaper;

    @Contract(pure = true)
    public WGRPChecker(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.wgrpBukkitBase = wgrpBukkitPlugin.getWgrpBukkitBase();
    }

    public void checkStartUpVersionServer() {
        if (!RSApi.isVersionSupported()) {
            log.error("""
                    This plugin version works only on 1.20+!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version of plugin for your server version.
                    """);
            wgrpBukkitBase.getServer().getPluginManager().disablePlugin(wgrpBukkitBase);
        }
    }

    public void checkIfRunningOnPaper() {
        isPaper = false;
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            isPaper = true;
        } catch (ClassNotFoundException ignored) {
            log.info(String.format("""
                            Using paper: %s
                            Better if you are running your server on paper or other forks of paper.
                            Please don't use any untrusted forks.
                            """, isPaper()));
        }
        log.info(String.format("Using paper: %s", isPaper()));
    }

    public void notifyAboutBuild() {
        if (wgrpBukkitBase.getDescription().getVersion().contains("alpha")
                || wgrpBukkitBase.getDescription().getVersion().contains("beta")
                || wgrpBukkitBase.getDescription().getVersion().contains("pre")) {
            log.warn("""
                     This is a test build. This building may be unstable!
                     When reporting a bug:
                     Use the issue tracker! Don't report bugs in the reviews.
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                     Provide as much information as possible.
                     Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                     Provide any stack traces or "errors" using pastebin.
                    """);
        } else {
            log.info("This is the latest stable building.");
        }
        log.info(String.format(""" 
                Using %s language version %s. Author of this localization - %s.
                """,
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.language"),
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.version"),
                wgrpBukkitPlugin.getConfigLoader().getMessages().get("langTitle.author")
        ));
    }

}
