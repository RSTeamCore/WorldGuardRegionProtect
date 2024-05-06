package wgrp.util.config.loader;

import net.ritasister.wgrp.core.api.config.Container;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import wgrp.WorldGuardRegionProtectBukkitBase;
import wgrp.util.config.Config;
import wgrp.util.config.InitConfig;
import wgrp.util.config.InitMessages;
import wgrp.util.config.ParamsVersionCheckImpl;

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
