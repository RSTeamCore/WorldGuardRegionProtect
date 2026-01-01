package net.ritasister.wgrp.util.config.provider;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.provider.MessageProvider;
import net.ritasister.wgrp.util.config.field.ConfigFields;
import net.ritasister.wgrp.util.config.files.Messages;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class MessagesProvider implements MessageProvider<WorldGuardRegionProtectPaperPlugin, Messages> {

    private Messages messages;

    @Override
    public void init(@NotNull final WorldGuardRegionProtectPaperPlugin plugin) {
        final String lang = ConfigFields.LANG.asString(plugin);
        final File file = new File(plugin.getBootstrap().getLoader().getDataFolder(), "lang/" + lang + ".yml");

        if (!file.exists()) {
            plugin.getBootstrap().getLoader().saveResource("lang/" + lang + ".yml", false);
        }

        this.messages = new Messages(YamlConfiguration.loadConfiguration(file));
        plugin.getLogger().info("Messages loaded for language: " + lang);
    }

    @Override
    public Messages get() {
        if (messages == null) {
            throw new IllegalStateException("message provider is not initialized yet! Call init(plugin) first.");
        }
        return messages;
    }
}
