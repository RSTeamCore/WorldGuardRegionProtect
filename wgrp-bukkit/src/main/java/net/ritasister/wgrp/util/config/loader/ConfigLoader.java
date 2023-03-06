package net.ritasister.wgrp.util.config.loader;

import lombok.Getter;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.abstracts.AbstractInitMessage;
import net.ritasister.wgrp.util.config.impl.InitConfigImpl;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;

public class ConfigLoader extends InitConfigImpl<WorldGuardRegionProtect> {

    @Getter
    private Config config;

    @Getter
    private Container messages;

    public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
        config = new Config(wgRegionProtect, wgrpBukkitPlugin);

        AbstractInitMessage abstractInitMessage = new MessageLoader();
        messages = abstractInitMessage.initMessages(wgrpBukkitPlugin, config);
        abstractInitMessage.checkVersionLang(wgrpBukkitPlugin, config);

        Bukkit.getLogger().info("All configs load successfully!");
    }

}
