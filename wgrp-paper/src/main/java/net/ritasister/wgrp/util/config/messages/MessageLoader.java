package net.ritasister.wgrp.util.config.messages;

import lombok.AllArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@AllArgsConstructor
public class MessageLoader {

    public Messages initMessages(@NotNull final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        final String lang = ConfigFields.LANG.get(wgrpPlugin.getWgrpPaperBase()).toString();
        final File file = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpPlugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", false);
        }
        return new Messages(YamlConfiguration.loadConfiguration(file));
    }

}
