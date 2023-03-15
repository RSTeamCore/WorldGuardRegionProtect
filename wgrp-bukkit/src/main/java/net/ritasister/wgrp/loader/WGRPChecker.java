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

    private final WorldGuardRegionProtect worldGuardRegionProtect;

    void checkStartUpVersionServer() {
        if (!worldGuardRegionProtect.getWgrpContainer().getRsApi().isVersionSupported()) {
            WGRPContainer.getLogger().error("""
                    This plugin version works only on 1.19+!
                    Please read this thread: https://www.spigotmc.org/resources/81321/
                    The main post on spigotmc and please download the correct version for your server version.
                    """);
        }
    }

    void checkIfRunningOnPaper() {
        worldGuardRegionProtect.getWgrpContainer().isPaper = false;
        try {
            // Any other works, just the shortest I could find.
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            worldGuardRegionProtect.getWgrpContainer().isPaper = true;
        } catch (ClassNotFoundException ignored) {
            WGRPContainer.getLogger().info(
                    "Better if you are running your server on paper or other forks of paper");
        }
        WGRPContainer.getLogger().info("Paper: " + worldGuardRegionProtect.getWgrpContainer().isPaper);
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
                     Please search for duplicates before reporting a new https://github.com/RSTeamCore/WorldGuardRegionProtect/issues!
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
