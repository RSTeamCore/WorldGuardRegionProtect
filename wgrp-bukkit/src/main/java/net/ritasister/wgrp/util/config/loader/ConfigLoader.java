package net.ritasister.wgrp.util.config.loader;

import lombok.Getter;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.AbstractInitMessage;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.InitConfigImpl;
import net.rsteamcore.config.Container;

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

        WGRPContainer.getLogger().info("All configs load successfully!");
    }

}
