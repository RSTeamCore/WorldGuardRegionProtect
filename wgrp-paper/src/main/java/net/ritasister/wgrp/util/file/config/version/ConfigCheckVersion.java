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
    public void check(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        versionUpdateService.checkAndUpdate(wgrpPlugin,
                ConfigType.CONFIG,
                new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "config.yml"),
                "config.yml");
    }
}
