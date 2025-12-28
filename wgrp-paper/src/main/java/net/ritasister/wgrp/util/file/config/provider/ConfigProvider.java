package net.ritasister.wgrp.util.file.config.provider;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.config.files.Config;
import org.jspecify.annotations.NonNull;

public class ConfigProvider implements net.ritasister.wgrp.api.config.provider.ConfigProvider<WorldGuardRegionProtectPaperPlugin, Config> {

    private Config config;

    @Override
    public void init(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        this.config = new Config(plugin);
    }

    @Override
    public Config get() {
        if (config == null) {
            throw new IllegalStateException("config provider is not initialized yet! Call init(plugin) first.");
        }
        return config;
    }
}
