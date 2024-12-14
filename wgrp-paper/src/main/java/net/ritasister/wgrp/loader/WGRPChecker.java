package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.util.utility.VersionCheck;
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
        if (!wgrpPlugin.getVersionCheck().isVersionSupported()) {
            wgrpPlugin.getLogger().severe(String.format("""
                    \n====================================================
                    
                            WorldGuardRegionProtect works only on %s!
                            Please read this thread: https://www.spigotmc.org/resources/81321/
                            The main post on spigotmc and please download the correct version of plugin for your server version.
                    
                    ====================================================
                    """, VersionCheck.SUPPORTED_VERSION_RANGE
            ));
            Bukkit.getServer().getPluginManager().disablePlugin(wgrpPlugin.getWgrpPaperBase());
        }
    }

    /**
     * Detect what is a trusted or an untrusted platform used.
     */
    public void detectWhatIsPlatformRun() {
        final String minecraftVersion = Bukkit.getServer().getMinecraftVersion();
        final String pluginVersion = wgrpPlugin.getWgrpPaperBase().getDescription().getVersion();
        if (wgrpPlugin.getType() == Platform.Type.PAPER || wgrpPlugin.getType() == Platform.Type.FOLIA) {
            try {
                Class.forName("com.destroystokyo.paper.ParticleBuilder");
                detectTrustPlatformMessage(pluginVersion, Platform.Type.PAPER.getPlatformName(), minecraftVersion);
            } catch (ClassNotFoundException ignored) {
                detectUnTrustPlatformMessage(pluginVersion, minecraftVersion);
            }
        } else {
            try {
                Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
                detectTrustPlatformMessage(pluginVersion, Platform.Type.FOLIA.getPlatformName(), minecraftVersion);
            } catch (ClassNotFoundException ignored) {
                detectUnTrustPlatformMessage(pluginVersion, minecraftVersion);
            }
        }
        if (wgrpPlugin.getType() == Platform.Type.SPIGOT) {
            try {
                Class.forName("com.destroystokyo.paper.ParticleBuilder");
            } catch (ClassNotFoundException ignored) {
                detectTrustPlatformMessage(pluginVersion, Platform.Type.SPIGOT.getPlatformName(), minecraftVersion);
            }
        }
    }

    private void detectTrustPlatformMessage(final String pluginVersion, final String type, final @NotNull String minecraftVersion) {
        wgrpPlugin.getLogger().info(String.format("""
                \n====================================================
                
                        WorldGuardRegionProtect %s
                        Server implementation running on %s - %s
                
                ====================================================
                """, pluginVersion, type, minecraftVersion));
    }

    private void detectUnTrustPlatformMessage(final String pluginVersion, final @NotNull String minecraftVersion) {
        wgrpPlugin.getLogger().info(String.format("""
                \n====================================================
                
                        WorldGuardRegionProtect %s
                        Server implementation running on %s - %s
                
                        Better if you are running your server on paper or other forks from paper.
                        Please don't use any untrusted/unknown forks.
                        You don't get support if you are not running on trust servers implementation.
                
                ====================================================
                """, pluginVersion, Platform.Type.UNKNOWN, minecraftVersion));
    }

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
