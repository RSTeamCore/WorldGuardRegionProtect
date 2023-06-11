package net.ritasister.wgrp.util.config.loader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.InitConfigImpl;
import net.ritasister.wgrp.util.config.InitMessages;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import net.rsteamcore.config.Container;

@Slf4j
public class ConfigLoader extends InitConfigImpl<WorldGuardRegionProtect> {

    @Getter
    private Config config;

    @Getter
    private Container messages;

    public void initConfig(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
        config = new Config(wgRegionProtect, wgrpBukkitPlugin);

        InitMessages initMessages = new MessageLoader();
        messages = initMessages.initMessages(wgrpBukkitPlugin, config);
        MessageCheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersionLang(wgrpBukkitPlugin, config);

        log.info("All configs load successfully!");
    }

}
