package net.ritasister.wgrp.util.file.messages;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.ritasister.wgrp.util.config.messages.ComponentWrapper;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Messages {

    private final ComponentWrapper DEFAULT_NULLABLE_MESSAGE = new ComponentWrapper("", this);

    public String prefix;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    private final Map<String, ComponentWrapper> langStrings = new HashMap<>();

    private final ComponentWrapper nullable = null;

    public Messages(ConfigurationSection section) {
        try {
            prefix = Objects.requireNonNull(section.getString("prefix"));
        } catch (NullPointerException e) {
            prefix = null;
        }
        final Map<String, ComponentWrapper> data = fromConfigurationToMap(section);
        langStrings.putAll(data);
    }

    private @NotNull Map<String, ComponentWrapper> fromConfigurationToMap(@NotNull ConfigurationSection section) {
        final Map<String, ComponentWrapper> data = new HashMap<>();
        for (String key : section.getKeys(false)) {
            if (section.isConfigurationSection(key)) {
                final ConfigurationSection subSection = section.getConfigurationSection(key);
                if (subSection != null) {
                    for (Map.Entry<String, ComponentWrapper> entry : fromConfigurationToMap(subSection).entrySet()) {
                        final String keyMessage = entry.getKey();
                        final ComponentWrapper message = entry.getValue();
                        if (prefix != null) message.setPrefix(prefix);
                        data.put(key + "." + keyMessage, message);
                    }
                }
            } else {
                if (section.isString(key)) {
                    data.put(key, new ComponentWrapper(section.getString(key), this));
                } else if (section.isList(key)) {
                    data.put(key, new ComponentWrapper(new ArrayList<>(section.getStringList(key)), this));
                }
            }
        }
        return data;
    }

    public boolean has(String key) {
        return langStrings.containsKey(key);
    }

    public ComponentWrapper get(String key) {
        final ComponentWrapper message = langStrings.get(key);
        if (message == null) return nullable != null ? nullable : DEFAULT_NULLABLE_MESSAGE;
        if (prefix != null) message.setPrefix(prefix);
        return message;
    }

    public ComponentWrapper get(String key, boolean withPrefix) {
        final ComponentWrapper message = langStrings.get(key);
        if (message == null) return nullable != null ? nullable : DEFAULT_NULLABLE_MESSAGE;
        if (prefix != null && withPrefix) message.setPrefix(prefix);
        return message;
    }

    public void put(String key, ComponentWrapper message) {
        langStrings.put(key, message);
    }

    public void putIfAbsent(String key, ComponentWrapper message) {
        langStrings.putIfAbsent(key, message);
    }

    public boolean hasPrefix() {
        return prefix != null;
    }
}
