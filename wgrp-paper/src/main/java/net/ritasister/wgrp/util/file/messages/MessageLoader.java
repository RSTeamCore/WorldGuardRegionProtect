package net.ritasister.wgrp.util.file.messages;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class MessageLoader {

    @Contract("_ -> new")
    public @NotNull Messages initMessages(@NotNull final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        final String lang = ConfigFields.LANG.get(wgrpPlugin).toString();
        final File file = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpPlugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", false);
        }
        return new Messages(YamlConfiguration.loadConfiguration(file));
    }

}
