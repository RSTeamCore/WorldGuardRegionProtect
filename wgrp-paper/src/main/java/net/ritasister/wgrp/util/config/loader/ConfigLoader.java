package net.ritasister.wgrp.util.config.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.core.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.InitConfig;
import net.ritasister.wgrp.util.config.InitMessages;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class ConfigLoader implements InitConfig<WorldGuardRegionProtectBukkitBase> {

    private Config config;

    private Container messages;

    public void initConfig(WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        this.config = new Config(wgrpBukkitBase);
        InitMessages<Plugin> initMessages = new MessageLoader();
        this.messages = initMessages.initMessages(wgrpBukkitBase, this.config);
        MessageCheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersionLang(wgrpBukkitBase, this.config);
        Bukkit.getLogger().info("All configs load successfully!");
    }

    public Config getConfig() {
        return config;
    }

    public Container getMessages() {
        return messages;
    }

}
