package net.ritasister.wgrp.util.file.config.version;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.version.VersionChecker;
import net.ritasister.wgrp.util.file.config.ConfigType;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class MessageCheckVersion implements VersionChecker<WorldGuardRegionProtectPaperPlugin> {

    private final VersionUpdateService versionUpdateService;

    public MessageCheckVersion(VersionUpdateService versionUpdateService) {
        this.versionUpdateService = versionUpdateService;
    }

    @Override
    public void check(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        versionUpdateService.checkAndUpdate(
                wgrpPlugin,
                ConfigType.LANG,
                new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(),
                        "lang/" + ConfigFields.LANG.asString(wgrpPlugin.getWgrpPaperBase()) + ".yml"),
                "lang/" + ConfigFields.LANG.asString(wgrpPlugin.getWgrpPaperBase()) + ".yml");
    }
}
