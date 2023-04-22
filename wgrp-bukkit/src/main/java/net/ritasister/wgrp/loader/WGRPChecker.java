package net.ritasister.wgrp.loader;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.rsteamcore.config.Container;
import org.jetbrains.annotations.NotNull;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WGRPChecker {

    private final WorldGuardRegionProtect wgRegionProtect;

    void checkStartUpVersionServer() {
        if (!wgRegionProtect.getWgrpContainer().getRsApi().isVersionSupported()) {
            WGRPContainer.getLogger().error("""
                    This plugin version works only on 1.19 - 1.19.4!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version for your server version.
                    """);
            wgRegionProtect.getWgrpContainer().getPluginManager().disablePlugin(wgRegionProtect.getWGRPBukkitPlugin());
        }
    }

    void checkIfRunningOnPaper() {
        wgRegionProtect.getWgrpContainer().isPaper = false;
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            wgRegionProtect.getWgrpContainer().isPaper = true;
        } catch (ClassNotFoundException ignored) {
            WGRPContainer.getLogger().info(String.format("""
                            Using paper? %s
                            Better if you are running your server on paper or other forks of paper.
                            Please don't use any untrusted forks.
                            """, wgRegionProtect.getWgrpContainer().isPaper));
        }
        WGRPContainer.getLogger().info(String.format("Using paper or trust forks of paper? %s Nice!", wgRegionProtect.getWgrpContainer().isPaper));
    }

    public void notifyAboutBuild(@NotNull WGRPContainer wgrpContainer) {
        String pluginVersion = wgrpContainer.getPluginVersion();
        Container container = wgrpContainer.getMessages();
        if (pluginVersion.contains("alpha")
                || pluginVersion.contains("beta")
                || pluginVersion.contains("pre")) {
            WGRPContainer.getLogger().warn("""
                     This is a test build. This building may be unstable!
                     When reporting a bug:
                     Use the issue tracker! Don't report bugs in the reviews.
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/wgRegionProtect/issues!
                     Provide as much information as possible.
                     Provide your WorldGuardRegionProtect version and Spigot/Paper version.
                     Provide any stack traces or "errors" using pastebin.
                    """);
        } else {
            WGRPContainer.getLogger().info("This is the latest stable building.");
        }
        WGRPContainer.getLogger().info(String.format(""" 
                Using %s language version %s. Author of this localization - %s.
                """, container.get("langTitle.language"), container.get("langTitle.version"), container.get("langTitle.author")
        ));
    }

}
