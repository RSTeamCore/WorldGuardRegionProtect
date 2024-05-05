package net.ritasister.wgrp.core.util.config.loader;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.core.util.config.Config;
import net.ritasister.wgrp.core.util.config.InitMessages;
import net.rsteamcore.config.Container;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Slf4j
@AllArgsConstructor
public class MessageLoader implements InitMessages<Plugin> {

    @Override
    public Container initMessages(@NotNull final Plugin wgrpBukkitBase, final @NotNull Config config) {
        String lang = config.getLang();
        File file = new File(wgrpBukkitBase.getDataFolder(), "lang/" + lang + ".yml");
        if (!file.exists()) {
            wgrpBukkitBase.saveResource("lang/" + lang + ".yml", false);
        }
        return new Container(YamlConfiguration.loadConfiguration(file));
    }

}
