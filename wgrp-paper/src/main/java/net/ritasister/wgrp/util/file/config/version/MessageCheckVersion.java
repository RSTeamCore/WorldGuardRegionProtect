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
    public boolean check(@NotNull WorldGuardRegionProtectPaperPlugin plugin) {
        final File langFile = new File(plugin.getBootstrap().getLoader().getDataFolder(),
                "lang/" + ConfigFields.LANG.asString(plugin) + ".yml");

        try {
            versionUpdateService.checkAndUpdate(plugin, ConfigType.LANG, langFile, "lang/" + ConfigFields.LANG.asString(plugin) + ".yml");
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to check/update language file: " + e.getMessage());
            return false;
        }
    }
}
