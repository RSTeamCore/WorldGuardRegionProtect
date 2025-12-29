package net.ritasister.wgrp.util.file.config.files;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
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

    private final WorldGuardRegionProtectPaperPlugin wgrpPaperBase;

    private Map<String, List<String>> regionProtect;
    private Map<String, List<String>> playerRegionProtect;
    private Map<String, List<String>> regionProtectAllow;
    private Map<String, List<String>> regionProtectOnlyBreakAllow;

    public Config(WorldGuardRegionProtectPaperPlugin wgrpPaperBase) {
        this.wgrpPaperBase = wgrpPaperBase;
        this.reloadConfig();
    }

    public void reloadConfig() {
        regionProtect = new HashMap<>();
        playerRegionProtect = new HashMap<>();
        regionProtectAllow = new HashMap<>();
        regionProtectOnlyBreakAllow = new HashMap<>();

        wgrpPaperBase.getBootstrap().getLoader().saveDefaultConfig();
        wgrpPaperBase.getBootstrap().getLoader().reloadConfig();

        for (ConfigFields field : ConfigFields.values()) {
            try {
                final Object value = this.getValue(field);
                saveConfig(field.getPath(), value);
            } catch (Exception e) {
                wgrpPaperBase.getLogger().severe("Could not load config.yml for " + field.name() + "! Error: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
        this.loadRegionProtectConfig();
    }

    private Object getValue(@NonNull ConfigFields field) {
        return switch (field.getFieldType()) {
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

    public Map<String, List<String>> getPlayerRegionProtectMap() {
        return playerRegionProtect;
    }

    public void setPlayerRegionProtectMap(@NotNull Map<String, List<String>> value) {
        playerRegionProtect = value;
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

    public void loadRegionProtectConfig() {
        final Configuration configFile = wgrpPaperBase.getBootstrap().getLoader().getConfig();

        loadMap(configFile, "wgRegionProtect.regionProtect", regionProtect);
        loadMap(configFile, "wgRegionProtect.playerRegionProtect", playerRegionProtect);
        loadMap(configFile, "wgRegionProtect.regionProtectAllow", regionProtectAllow);
        loadMap(configFile, "wgRegionProtect.regionProtectOnlyBreakAllow", regionProtectOnlyBreakAllow);

        for (World w : Bukkit.getWorlds()) {
            regionProtect.putIfAbsent(w.getName(), new ArrayList<>());
            playerRegionProtect.putIfAbsent(w.getName(), new ArrayList<>());
            regionProtectAllow.putIfAbsent(w.getName(), new ArrayList<>());
            regionProtectOnlyBreakAllow.putIfAbsent(w.getName(), new ArrayList<>());
        }

        this.logRegionProtectConfig();
    }

    public void saveRegionProtectConfig() {
        final Configuration configFile = wgrpPaperBase.getBootstrap().getLoader().getConfig();

        configFile.set("wgRegionProtect.regionProtect", regionProtect);
        configFile.set("wgRegionProtect.playerRegionProtect", playerRegionProtect);
        configFile.set("wgRegionProtect.regionProtectAllow", regionProtectAllow);
        configFile.set("wgRegionProtect.regionProtectOnlyBreakAllow", regionProtectOnlyBreakAllow);

        wgrpPaperBase.getBootstrap().getLoader().saveConfig();
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
        logRegionMap("=== Loaded fully protected regions ===", regionProtect);
        logRegionMap("=== Loaded player protected regions ===", playerRegionProtect);
        logRegionMap("=== Loaded regions allowing interact ===", regionProtectAllow);
        logRegionMap("=== Loaded regions allowing only break events ===", regionProtectOnlyBreakAllow);
    }

    private void logRegionMap(String header, @NonNull Map<String, List<String>> regions) {
        wgrpPaperBase.getLogger().info(header);

        if (regions.isEmpty()) {
            wgrpPaperBase.getLogger().info("  No regions configured.");
            return;
        }

        regions.forEach((world, list) -> {
            final String output = list.isEmpty() ? "None" : String.join(", ", list);
            wgrpPaperBase.getLogger().info(String.format("  %-15s : [%s]", world, output));
        });
    }

    public void saveConfig(final String path, final Object field) {
        try {
            wgrpPaperBase.getBootstrap().getLoader().getConfig().set(path, field);
            wgrpPaperBase.getBootstrap().getLoader().saveConfig();
        } catch (Exception e) {
            wgrpPaperBase.getLogger().severe("Could not save config.yml! Error: " + e.getMessage());
            e.fillInStackTrace();
        }
    }

    public void saveConfigFiles() {
        wgrpPaperBase.getLogger().info("Saving all configuration files before the plugin shuts down...");
        for (ConfigFields field : ConfigFields.values()) {
            try {
                final Object value = this.getValue(field);

                this.saveConfig(field.getPath(), value);
                wgrpPaperBase.getLogger().info(String.format("Checking and saving field: %s = %s", field.name(), value));

            } catch (Exception e) {
                wgrpPaperBase.getLogger().severe("Could not save config.yml for field " + field.name() + "! Error: " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }

}
