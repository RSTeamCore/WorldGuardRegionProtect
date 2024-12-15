package net.ritasister.wgrp.rslibs.updater;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;

public class UpdateDownloader {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public UpdateDownloader(WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    void downloadUpdatePlugin() {
        final String owner = "RSTeamCore";
        final String pluginName = wgrpPlugin.getWgrpPaperBase().getName();
        final String version = wgrpPlugin.getWgrpPaperBase().getDescription().getVersion();
        final String fileName = String.format("WorldGuardRegionProtect-%s.jar", version);
        final String pluginPath = wgrpPlugin.getWgrpPaperBase().getDataFolder().getAbsolutePath();

        wgrpPlugin.getLogger().info("Download process has been successfully initiated.");
        wgrpPlugin.getDownloader().downloadLatestJar(owner, pluginName, fileName, pluginPath);
    }
}
