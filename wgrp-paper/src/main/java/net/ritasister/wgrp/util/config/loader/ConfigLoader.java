package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.config.InitConfig;
import net.ritasister.wgrp.api.config.InitMessages;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader implements InitConfig<WorldGuardRegionProtectBukkitPlugin> {

    private Config config;

    private Container messages;

    public void initConfig(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.config = new Config(wgrpBukkitPlugin.getWgrpBukkitBase());

        //Init messages
        InitMessages<WorldGuardRegionProtectBukkitPlugin, Config, Container> messageLoader = new MessageLoader();
        this.messages = messageLoader.initMessages(wgrpBukkitPlugin, this.config);

        //Check a config version
        CheckVersion configCheckVersion = new ConfigCheckVersion(new ParamsVersionCheckImpl());
        configCheckVersion.checkVersion(wgrpBukkitPlugin);

        //Check lang a version of file
        CheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersion(wgrpBukkitPlugin, this.config);

        wgrpBukkitPlugin.getPluginLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Container getMessages() {
        return messages;
    }

}
