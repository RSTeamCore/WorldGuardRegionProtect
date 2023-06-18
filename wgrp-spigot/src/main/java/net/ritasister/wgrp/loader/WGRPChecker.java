package net.ritasister.wgrp.loader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.api.RSApi;

@Slf4j
public class WGRPChecker {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;
    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    @Getter
    private boolean isPaper;

    public WGRPChecker(final WorldGuardRegionProtectBukkitBase wgrpBukkitBase,
                       final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitBase = wgrpBukkitBase;
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
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
                            You are not using a paper? %s
                            Better if you are running your server on paper or other forks of paper.
                            Please don't use any untrusted forks.
                            """, isPaper()));
        }
        log.info(String.format("Using paper or trust forks of paper? %s Nice!", isPaper()));
    }

    public void notifyAboutBuild() {
        if (wgrpBukkitBase.getPluginMeta().getVersion().contains("alpha")
                || wgrpBukkitBase.getPluginMeta().getVersion().contains("beta")
                || wgrpBukkitBase.getPluginMeta().getVersion().contains("pre")) {
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
