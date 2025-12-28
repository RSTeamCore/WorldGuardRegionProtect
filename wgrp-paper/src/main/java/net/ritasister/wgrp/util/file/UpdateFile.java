package net.ritasister.wgrp.util.file;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.FileUpdater;
import net.ritasister.wgrp.api.config.version.DateProvider;
import net.ritasister.wgrp.util.file.config.ConfigType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class UpdateFile implements FileUpdater<WorldGuardRegionProtectPaperPlugin> {

    private final DateProvider dateProvider;

    public UpdateFile(@NotNull DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    @Override
    public void updateConfig(@NotNull WorldGuardRegionProtectPaperPlugin plugin, @NotNull File currentFile) {
        final Path renameOldFile = new File(plugin.getWgrpPaperBase().getDataFolder(), "config-old-" + dateProvider.getCurrentDate() + ".yml").toPath();
        try {
            Files.move(currentFile.toPath(), renameOldFile, StandardCopyOption.REPLACE_EXISTING);
            plugin.getLogger().info("Old config file is renamed to: " + renameOldFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        plugin.getWgrpPaperBase().saveResource("config.yml", true);
    }

    @Override
    public void updateLang(@NotNull WorldGuardRegionProtectPaperPlugin plugin,
                           @NotNull File currentFile, @NotNull String lang) {
        final Path renameOldLang = new File(plugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + "-old-" + dateProvider.getCurrentDate() + ".yml").toPath();
        try {
            Files.move(currentFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
            plugin.getLogger().info("Old lang file is renamed to: " + renameOldLang);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        plugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", true);
    }
}
