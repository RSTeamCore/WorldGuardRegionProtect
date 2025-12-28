package net.ritasister.wgrp.util.file.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.provider.ConfigProvider;
import net.ritasister.wgrp.api.config.provider.MessageProvider;
import net.ritasister.wgrp.api.config.version.VersionChecker;
import net.ritasister.wgrp.util.file.config.files.Config;
import net.ritasister.wgrp.util.file.config.files.Messages;

public final class ConfigLoader {

    private final ConfigProvider<WorldGuardRegionProtectPaperPlugin, Config> configProvider;
    private final MessageProvider<WorldGuardRegionProtectPaperPlugin, Messages> messageProvider;
    private final VersionChecker<WorldGuardRegionProtectPaperPlugin> configVersionChecker;
    private final VersionChecker<WorldGuardRegionProtectPaperPlugin> langVersionChecker;

    public ConfigLoader(
            ConfigProvider<WorldGuardRegionProtectPaperPlugin, Config> configProvider,
            MessageProvider<WorldGuardRegionProtectPaperPlugin, Messages> messageProvider,
            VersionChecker<WorldGuardRegionProtectPaperPlugin> configVersionChecker,
            VersionChecker<WorldGuardRegionProtectPaperPlugin> langVersionChecker
    ) {
        this.configProvider = configProvider;
        this.messageProvider = messageProvider;
        this.configVersionChecker = configVersionChecker;
        this.langVersionChecker = langVersionChecker;
    }

    public void loadFiles(WorldGuardRegionProtectPaperPlugin plugin) {
        configVersionChecker.check(plugin);
        langVersionChecker.check(plugin);

        configProvider.init(plugin);
        messageProvider.init(plugin);
    }

    public long reload(WorldGuardRegionProtectPaperPlugin plugin) {
        final long start = System.currentTimeMillis();

        configVersionChecker.check(plugin);
        langVersionChecker.check(plugin);

        configProvider.init(plugin);
        messageProvider.init(plugin);

        return System.currentTimeMillis() - start;
    }

    public Config getConfig() {
        return configProvider.get();
    }

    public Messages getMessages() {
        return messageProvider.get();
    }
}
