package net.ritasister.wgrp.util.file.config.provider;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.provider.MessageProvider;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import net.ritasister.wgrp.util.file.config.files.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class MessagesProvider implements MessageProvider<WorldGuardRegionProtectPaperPlugin, Messages> {

    private Messages messages;

    @Override
    public void init(@NotNull final WorldGuardRegionProtectPaperPlugin plugin) {
        final String lang = ConfigFields.LANG.asString(plugin.getWgrpPaperBase());
        final File file = new File(plugin.getWgrpPaperBase().getDataFolder(), "lang/" + lang + ".yml");

        if (!file.exists()) {
            plugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", false);
        }

        this.messages = new Messages(YamlConfiguration.loadConfiguration(file));
        plugin.getLogger().info("Messages loaded for language: " + lang);
    }

    @Override
    public Messages get() {
        return messages;
    }
}
