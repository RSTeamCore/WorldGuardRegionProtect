package net.ritasister.wgrp.util.config.loader;

import lombok.AllArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@AllArgsConstructor
public class MessageLoader implements InitMessages<WorldGuardRegionProtectPaperPlugin, Config, Container> {

    @Override
    public Container initMessages(@NotNull final WorldGuardRegionProtectPaperPlugin wgrpPlugin, final @NotNull Config config) {
        final String lang = ConfigFields.LANG.get(wgrpPlugin.getWgrpPaperBase()).toString();
        File file = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpPlugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", false);
        }
        return new Container(YamlConfiguration.loadConfiguration(file));
    }

}
