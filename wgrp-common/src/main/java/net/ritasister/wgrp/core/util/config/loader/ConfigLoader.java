package net.ritasister.wgrp.core.util.config.loader;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.util.config.Config;
import net.ritasister.wgrp.core.util.config.InitConfig;
import net.ritasister.wgrp.core.util.config.InitMessages;
import net.ritasister.wgrp.core.util.config.ParamsVersionCheckImpl;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ConfigLoader implements InitConfig<WorldGuardRegionProtectPlugin> {

    private Config config;

    private Container messages;

    public void initConfig(WorldGuardRegionProtectPlugin wgrpPlugin) {
        config = new Config((Plugin) wgrpPlugin);

        InitMessages<Plugin> initMessages = new MessageLoader();
        messages = initMessages.initMessages((Plugin) wgrpPlugin, config);
        MessageCheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersionLang((Plugin) wgrpPlugin, config);

        Bukkit.getLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Container getMessages() {
        return messages;
    }

}
