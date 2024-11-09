package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.rslibs.api.RSApiImpl;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for checking all instances of plugin.
 */
public class WGRPChecker {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public WGRPChecker(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    /**
     * Check all variables while the plugin is startup.
     */
    public void checkStartUpVersionServer() {
        if (!wgrpPlugin.getRsApi().isVersionSupported()) {
            wgrpPlugin.getLogger().severe(String.format("""
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
    public void detectWhatIsPlatformRun() {
        final String minecraftVersion = Bukkit.getServer().getMinecraftVersion();
        if (wgrpPlugin.getType() == Platform.Type.PAPER) {
            try {
                Class.forName("com.destroystokyo.paper.ParticleBuilder");
                detectTrustPlatformMessage(Platform.Type.PAPER.getPlatformName(), minecraftVersion);
            } catch (ClassNotFoundException ignored) {
                detectUnTrustPlatformMessage(minecraftVersion);
            }
        } else if (wgrpPlugin.getType() == Platform.Type.FOLIA) {
            try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
                detectTrustPlatformMessage(Platform.Type.FOLIA.getPlatformName(), minecraftVersion);
            } catch (ClassNotFoundException ignored) {
                detectUnTrustPlatformMessage(minecraftVersion);
            }
        }
    }

    private void detectTrustPlatformMessage(final String type, final @NotNull String minecraftVersion) {
        wgrpPlugin.getLogger().info(String.format("Server implementation running on %s as version %s.", type, minecraftVersion));
    }

    private void detectUnTrustPlatformMessage(final @NotNull String minecraftVersion) {
        wgrpPlugin.getLogger().info(String.format("""
                Server implementation running on %s as version %s.
                Better if you are running your server on paper or other forks of paper.
                Please don't use any untrusted forks.
                You don't get support if you are use other untrusted forks.
                """, Platform.Type.UNKNOWN, minecraftVersion));
    }

    /**
     * Notify about build this plugin. This is an alpha, beta or pre-release.
     */
    public void notifyAboutBuild() {
        wgrpPlugin.getLogger().info(String.format(
                """ 
                        Using %s language version %s. Author of this localization - %s.
                        """,
                wgrpPlugin.getConfigLoader().getMessages().get("langTitle.language"),
                wgrpPlugin.getConfigLoader().getMessages().get("langTitle.version"),
                wgrpPlugin.getConfigLoader().getMessages().get("langTitle.author")
        ));
    }

}
