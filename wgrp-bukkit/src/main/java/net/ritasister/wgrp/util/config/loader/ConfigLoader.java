package net.ritasister.wgrp.util.config.loader;

import lombok.Getter;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.abstracts.AbstractInitConfig;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;

public class ConfigLoader extends AbstractInitConfig<WorldGuardRegionProtect> {

    @Getter
    private Config config;

    @Getter
    private Container messages;

    public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
        config = new Config(wgRegionProtect, wgrpBukkitPlugin);
        MessageLoader messageLoader = new MessageLoader();
        messages = messageLoader.initMessages(wgrpBukkitPlugin, config);
        messageLoader.checkVersionLang(wgrpBukkitPlugin, config);
        Bukkit.getLogger().info("All configs load successfully!");
    }

}
