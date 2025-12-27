package net.ritasister.wgrp.util.file.config.files;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperBase;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Config implements net.ritasister.wgrp.api.config.Config {

    private final WorldGuardRegionProtectPaperBase wgrpPaperBase;

    private Map<String, List<String>> regionProtect;
    private Map<String, List<String>> regionProtectAllow;
    private Map<String, List<String>> regionProtectOnlyBreakAllow;

    public Config(WorldGuardRegionProtectPaperBase wgrpPaperBase) {
        this.wgrpPaperBase = wgrpPaperBase;
        this.reloadConfig();
    }

    public void reloadConfig() {
        regionProtect = new HashMap<>();
        regionProtectAllow = new HashMap<>();
        regionProtectOnlyBreakAllow = new HashMap<>();

        wgrpPaperBase.saveDefaultConfig();
        wgrpPaperBase.reloadConfig();

        for (ConfigFields field : ConfigFields.values()) {
            try {
                final Object value = switch (field.getFieldType()) {
                    case STRING -> field.asString(wgrpPaperBase);
                    case BOOLEAN -> field.asBoolean(wgrpPaperBase);
                    case INTEGER -> field.asInt(wgrpPaperBase);
                    case DOUBLE -> field.asDouble(wgrpPaperBase);
                    case LONG -> field.asLong(wgrpPaperBase);
                    case FLOAT -> field.asFloat(wgrpPaperBase);
                    case STRING_LIST -> field.asStringList(wgrpPaperBase);
                    case INTEGER_LIST -> field.asIntegerList(wgrpPaperBase);
                    case DOUBLE_LIST -> field.asDoubleList(wgrpPaperBase);
                    case LONG_LIST -> field.asLongList(wgrpPaperBase);
                    case FLOAT_LIST -> field.asFloatList(wgrpPaperBase);
                    case BOOLEAN_LIST -> field.asBooleanList(wgrpPaperBase);
                };
                saveConfig(field.getPath(), value);
            } catch (Exception e) {
                wgrpPaperBase.getLogger().severe("Could not load config.yml for " + field.name() + "! Error: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        this.saveRegionProtectConfig();
    }

    @Override
    public Map<String, List<String>> getRegionProtectMap() {
        return regionProtect;
    }

    @Override
    public void setRegionProtectMap(@NotNull Map<String, List<String>> value) {
        regionProtect = value;
        saveRegionProtectConfig();
    }

    @Override
    public Map<String, List<String>> getRegionProtectAllowMap() {
        return regionProtectAllow;
    }

    @Override
    public void setRegionProtectAllowMap(@NotNull Map<String, List<String>> value) {
        regionProtectAllow = value;
        saveRegionProtectConfig();
    }

    @Override
    public Map<String, List<String>> getRegionProtectOnlyBreakAllowMap() {
        return regionProtectOnlyBreakAllow;
    }

    @Override
    public void setRegionProtectOnlyBreakAllow(@NotNull Map<String, List<String>> value) {
        regionProtectOnlyBreakAllow = value;
        saveRegionProtectConfig();
    }

    public void saveRegionProtectConfig() {
        final Configuration configFile = wgrpPaperBase.getConfig();

        loadMap(configFile, "wgRegionProtect.regionProtect", regionProtect);
        loadMap(configFile, "wgRegionProtect.regionProtectAllow", regionProtectAllow);
        loadMap(configFile, "wgRegionProtect.regionProtectOnlyBreakAllow", regionProtectOnlyBreakAllow);

        for (World w : Bukkit.getWorlds()) {
            regionProtect.putIfAbsent(w.getName(), new ArrayList<>());
            regionProtectAllow.putIfAbsent(w.getName(), new ArrayList<>());
            regionProtectOnlyBreakAllow.putIfAbsent(w.getName(), new ArrayList<>());
        }

        this.logRegionProtectConfig();
    }

    private void loadMap(@NonNull Configuration configFile, String basePath, Map<String, List<String>> map) {
        final ConfigurationSection section = configFile.getConfigurationSection(basePath);
        if (section != null) {
            for (String key : section.getKeys(false)) {
                final List<String> list = section.getStringList(key);

                map.put(key, list);
            }
        } else {
            wgrpPaperBase.getLogger().info(basePath + " section not found, creating default map.");
            map.put("world", new ArrayList<>());
        }
    }

    private void logRegionProtectConfig() {
        wgrpPaperBase.getLogger().info("=== Loaded fully protected region ===");
        regionProtect.forEach((world, list) -> wgrpPaperBase.getLogger().info(world + ": " + list));

        wgrpPaperBase.getLogger().info("=== Loaded protected region with allowing interact ===");
        regionProtectAllow.forEach((world, list) -> wgrpPaperBase.getLogger().info(world + ": " + list));

        wgrpPaperBase.getLogger().info("=== Loaded protected region where allow only breaks event ===");
        regionProtectOnlyBreakAllow.forEach((world, list) -> wgrpPaperBase.getLogger().info(world + ": " + list));
    }


    public void saveConfig(final String path, final Object field) {
        try {
            wgrpPaperBase.getConfig().set(path, field);
            wgrpPaperBase.saveConfig();
        } catch (Exception e) {
            wgrpPaperBase.getLogger().severe("Could not save config.yml! Error: " + e.getMessage());
            e.fillInStackTrace();
        }
    }

    public void saveConfigFiles() {
        wgrpPaperBase.getLogger().info("Saving all configuration files before the plugin shuts down...");
        for (ConfigFields field : ConfigFields.values()) {
            try {
                final Object value = switch (field.getFieldType()) {
                    case STRING -> field.asString(wgrpPaperBase);
                    case BOOLEAN -> field.asBoolean(wgrpPaperBase);
                    case INTEGER -> field.asInt(wgrpPaperBase);
                    case DOUBLE -> field.asDouble(wgrpPaperBase);
                    case LONG -> field.asLong(wgrpPaperBase);
                    case FLOAT -> field.asFloat(wgrpPaperBase);
                    case STRING_LIST -> field.asStringList(wgrpPaperBase);
                    case INTEGER_LIST -> field.asIntegerList(wgrpPaperBase);
                    case DOUBLE_LIST -> field.asDoubleList(wgrpPaperBase);
                    case LONG_LIST -> field.asLongList(wgrpPaperBase);
                    case FLOAT_LIST -> field.asFloatList(wgrpPaperBase);
                    case BOOLEAN_LIST -> field.asBooleanList(wgrpPaperBase);
                };

                this.saveConfig(field.getPath(), value);
                wgrpPaperBase.getLogger().info(String.format("Checking and saving field: %s = %s", field.name(), value));

            } catch (Exception e) {
                wgrpPaperBase.getLogger().severe("Could not save config.yml for field " + field.name() + "! Error: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

}
