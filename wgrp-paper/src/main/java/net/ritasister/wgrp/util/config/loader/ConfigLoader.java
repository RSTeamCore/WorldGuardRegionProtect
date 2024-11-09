package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader {

    private Config config;

    private Container messages;

    public void initConfig(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.config = new Config(wgrpPlugin.getWgrpPaperBase());

        //Init messages
        wgrpPlugin.getPluginLogger().info("Started loading messages...");
        final MessageLoader messageLoader = new MessageLoader();
        this.messages = messageLoader.initMessages(wgrpPlugin);

        //Check a config version of file
        final CheckVersion configCheckVersion = new ConfigCheckVersion(new ParamsVersionCheckImpl());
        configCheckVersion.checkVersion(wgrpPlugin);

        //Check a lang version of file
        final CheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersion(wgrpPlugin);

        wgrpPlugin.getPluginLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Container getMessages() {
        return messages;
    }

}
