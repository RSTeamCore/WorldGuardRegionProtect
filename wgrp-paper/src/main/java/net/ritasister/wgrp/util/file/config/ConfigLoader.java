package net.ritasister.wgrp.util.file.config;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.CheckVersion;
import net.ritasister.wgrp.util.file.ParamsVersionCheckImpl;
import net.ritasister.wgrp.util.file.UpdateFile;
import net.ritasister.wgrp.util.file.messages.MessageCheckVersion;
import net.ritasister.wgrp.util.file.messages.MessageLoader;
import net.ritasister.wgrp.util.file.messages.Messages;
import org.jetbrains.annotations.NotNull;

public final class ConfigLoader {

    private Config config;
    private Messages messages;
    private UpdateFile updateFile;

    public void initConfig(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.updateFile = new UpdateFile(new ParamsVersionCheckImpl());

        //Initialising config.yml
        this.config = new Config(wgrpPlugin);

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

    public UpdateFile getUpdateFile() {
        return this.updateFile;
    }

    public Config getConfig() {
        return this.config;
    }

    public Messages getMessages() {
        return this.messages;
    }

}
