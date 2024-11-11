package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.config.messages.MessageCheckVersion;
import net.ritasister.wgrp.util.config.messages.MessageLoader;
import net.ritasister.wgrp.util.config.messages.Messages;
import org.jetbrains.annotations.NotNull;

public class ConfigLoader {

    private Config config;

    private Messages messages;

    public void initConfig(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        //Initialising config.yml
        this.config = new Config(wgrpPlugin.getWgrpPaperBase());

        //Check a config version if available a new version
        final CheckVersion configCheckVersion = new ConfigCheckVersion(new ParamsVersionCheckImpl());
        configCheckVersion.checkVersion(wgrpPlugin);

        //Initialising messages where located in like is /lang/en_US.yml
        wgrpPlugin.getLogger().info("Started loading messages...");
        final MessageLoader messageLoader = new MessageLoader();
        this.messages = messageLoader.initMessages(wgrpPlugin);

        //Check a lang version if available a new version
        final CheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersion(wgrpPlugin);

        wgrpPlugin.getLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Messages getMessages() {
        return messages;
    }

}
