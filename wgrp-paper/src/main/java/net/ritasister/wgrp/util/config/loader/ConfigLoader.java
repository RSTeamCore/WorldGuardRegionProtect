package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.InitConfig;
import net.ritasister.wgrp.util.config.InitMessages;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader implements InitConfig<WorldGuardRegionProtectBukkitPlugin> {

    private Config config;

    private Container messages;

    public void initConfig(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.config = new Config(wgrpBukkitPlugin.getWgrpBukkitBase());
        InitMessages<WorldGuardRegionProtectBukkitPlugin> initMessages = new MessageLoader();
        this.messages = initMessages.initMessages(wgrpBukkitPlugin, this.config);
        MessageCheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersionLang(wgrpBukkitPlugin, this.config);
        wgrpBukkitPlugin.getPluginLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Container getMessages() {
        return messages;
    }

}
