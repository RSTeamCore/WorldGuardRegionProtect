package net.ritasister.wgrp.util.config.loader;

import lombok.AllArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import net.ritasister.wgrp.api.config.InitMessages;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@AllArgsConstructor
public class MessageLoader implements InitMessages<WorldGuardRegionProtectBukkitBase, Config, Container> {

    @Override
    public Container initMessages(@NotNull final WorldGuardRegionProtectBukkitBase wgrpBukkitBase, final @NotNull Config config) {
        String lang = config.getLang();
        File file = new File(wgrpBukkitBase.getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpBukkitBase.saveResource("lang/" + lang + ".yml", false);
        }
        return new Container(YamlConfiguration.loadConfiguration(file));
    }

}
