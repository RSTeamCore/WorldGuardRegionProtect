package net.ritasister.wgrp.util.config.loader;

import lombok.AllArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.InitMessages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@AllArgsConstructor
public class MessageLoader implements InitMessages<WorldGuardRegionProtectBukkitPlugin> {

    @Override
    public Container initMessages(
            @NotNull final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin,
            final @NotNull Config config
    ) {
        String lang = config.getLang();
        File file = new File(wgrpBukkitPlugin.getWgrpBukkitBase().getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpBukkitPlugin.getWgrpBukkitBase().saveResource("lang/" + lang + ".yml", false);
        }
        return new Container(YamlConfiguration.loadConfiguration(file));
    }

}
