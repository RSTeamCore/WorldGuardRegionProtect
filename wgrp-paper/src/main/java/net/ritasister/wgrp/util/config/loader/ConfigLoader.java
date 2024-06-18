package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.api.config.InitConfig;
import net.ritasister.wgrp.api.config.InitMessages;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader implements InitConfig<WorldGuardRegionProtectBukkitBase> {

    private Config config;

    private Container messages;

    public void initConfig(@NotNull WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        this.config = new Config(wgrpBukkitBase);

        //Init messages
        InitMessages<WorldGuardRegionProtectBukkitBase, Config, Container> messageLoader = new MessageLoader();
        this.messages = messageLoader.initMessages(wgrpBukkitBase, this.config);

        //Check a config version
        CheckVersion configCheckVersion = new ConfigCheckVersion(new ParamsVersionCheckImpl());
        configCheckVersion.checkVersion(wgrpBukkitBase, this.config);

        //Check lang a version of file
        CheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersion(wgrpBukkitBase, this.config);

        wgrpBukkitBase.getApi().getPluginLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Container getMessages() {
        return messages;
    }

}
