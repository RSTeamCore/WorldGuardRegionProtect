package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import net.ritasister.wgrp.util.ServerType;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for checking all instances of plugin.
 */
public class WGRPChecker {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private boolean isPaper = false;
    private boolean isFolia = false;

    public WGRPChecker(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    /**
     * Check all variables while the plugin is startup.
     */
    public void checkStartUpVersionServer() {
        if (!wgrpPlugin.getRsApi().isVersionSupported()) {
            wgrpPlugin.getPluginLogger().severe(String.format("""
                    This plugin version works only on %s!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version of plugin for your server version.
                    """, RSApiImpl.SUPPORTED_VERSION_RANGE));
            Bukkit.getServer().getPluginManager().disablePlugin(wgrpPlugin.getWgrpPaperBase());
        }
    }

    /**
     * Detect what is a trusted or an untrusted platform used.
     */
    public void detectPlatformRun() {
        if (isPaper) {
            try {
                Class.forName("com.destroystokyo.paper.ParticleBuilder");
                detectTrustPlatformMessage(ServerType.PAPER);
                isPaper = true;
            } catch (ClassNotFoundException ignored) {
                detectUnTrustPlatformMessage();
            }
            wgrpPlugin.getPluginLogger().info(String.format("Using paper: %s", isPaper()));
        } else if (isFolia) {
            try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
                isFolia = true;
                detectTrustPlatformMessage(ServerType.FOLIA);
            } catch (ClassNotFoundException ignored) {
                detectUnTrustPlatformMessage();
            }
        }
    }

    private void detectTrustPlatformMessage(ServerType platform) {
        wgrpPlugin.getPluginLogger().info(String.format("""
                Your platform is: %s
                You will be able to get support.
                Running in folia can will be may issues, so please report any error.
                """, platform));
    }

    private void detectUnTrustPlatformMessage() {
        wgrpPlugin.getPluginLogger().info(String.format("""
                Your platform is: %s
                Better if you are running your server on paper or other forks of paper.
                Please don't use any untrusted forks.
                You don't get support if you are use other untrusted forks.
                """, ServerType.UNKNOWN));
    }

    /**
     * Notify about build this plugin. This is an alpha, beta or pre-release.
     */
    public void notifyAboutBuild() {
        if (wgrpPlugin.getWgrpPaperBase().getPluginMeta().getVersion().contains("alpha")
                || wgrpPlugin.getWgrpPaperBase().getPluginMeta().getVersion().contains("beta")
                || wgrpPlugin.getWgrpPaperBase().getPluginMeta().getVersion().contains("pre")) {
            wgrpPlugin.getPluginLogger().warn("""
                     This is a test build. This building may be unstable!
                     When reporting a bug:
                     Use the issue tracker! Don't report bugs in the reviews.
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
                     Provide as much information as possible.
                     Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                     Provide any stack traces or "errors" using pastebin.
                    """);
        } else {
            wgrpPlugin.getPluginLogger().info("This is the latest stable building.");
        }
        wgrpPlugin.getPluginLogger().info(String.format(
                """ 
                        Using %s language version %s. Author of this localization - %s.
                        """,
                wgrpPlugin.getConfigLoader().getMessages().get("langTitle.language"),
                wgrpPlugin.getConfigLoader().getMessages().get("langTitle.version"),
                wgrpPlugin.getConfigLoader().getMessages().get("langTitle.author")
        ));
    }

    public boolean isPaper() {
        return this.isPaper;
    }

    public boolean isFolia() {
        return this.isFolia;
    }

}
