package net.ritasister.wgrp.util.file.config.version;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.version.VersionChecker;
import net.ritasister.wgrp.util.file.config.ConfigType;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class ConfigCheckVersion implements VersionChecker<WorldGuardRegionProtectPaperPlugin> {

    private final VersionUpdateService versionUpdateService;

    public ConfigCheckVersion(VersionUpdateService versionUpdateService) {
        this.versionUpdateService = versionUpdateService;
    }

    @Override
    public boolean check(@NotNull WorldGuardRegionProtectPaperPlugin plugin) {
        final File configFile = new File(plugin.getWgrpPaperBase().getDataFolder(), "config.yml");

        try {
            versionUpdateService.checkAndUpdate(plugin, ConfigType.CONFIG, configFile, "config.yml");
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to check/update config.yml: " + e.getMessage());
            return false;
        }
    }
}
