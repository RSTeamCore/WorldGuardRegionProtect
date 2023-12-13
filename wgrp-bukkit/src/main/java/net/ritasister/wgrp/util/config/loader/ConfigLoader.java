package net.ritasister.wgrp.util.config.loader;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.InitConfig;
import net.ritasister.wgrp.util.config.InitMessages;
import net.ritasister.wgrp.util.config.ParamsVersionCheckImpl;
import net.rsteamcore.config.Container;

@Slf4j
public class ConfigLoader implements InitConfig<WorldGuardRegionProtectBukkitBase> {

    @Getter
    private Config config;

    @Getter
    private Container messages;

    public void initConfig(WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        config = new Config(wgrpBukkitBase);

        InitMessages initMessages = new MessageLoader();
        messages = initMessages.initMessages(wgrpBukkitBase, config);
        MessageCheckVersion messageCheckVersion = new MessageCheckVersion(new ParamsVersionCheckImpl());
        messageCheckVersion.checkVersionLang(wgrpBukkitBase, config);

        log.info("All configs load successfully!");
    }

}
