package net.ritasister.wgrp.util.file.config;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private Map<String, List<String>> regionProtect;
    private Map<String, List<String>> regionProtectAllow;
    private Map<String, List<String>> regionProtectOnlyBreakAllow;

    public Config(WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
        this.reloadConfig();
    }

    public void reloadConfig() {
        regionProtect = new HashMap<>();
        regionProtectAllow = new HashMap<>();
        regionProtectOnlyBreakAllow = new HashMap<>();

        wgrpPlugin.getWgrpPaperBase().saveDefaultConfig();
        wgrpPlugin.getWgrpPaperBase().reloadConfig();

        for (ConfigFields configFields : ConfigFields.values()) {
            try {
                configFields.get(wgrpPlugin);

                // Retrieve and populate region data
                regionProtectSection();
                regionProtectAllowSection();
                regionProtectOnlyBreakAllowSection();

                // Save the specific config field data after retrieval
                saveConfig(configFields.getPath(), configFields.get(wgrpPlugin));
            } catch (Exception exception) {
                wgrpPlugin.getLogger().severe("Error loading config.yml for field '"
                        + configFields.name() + "': " + exception.getMessage());
                wgrpPlugin.getLogger().severe("Stack trace: " + exception.getMessage());
            }
        }
    }

    private void regionProtectOnlyBreakAllowSection() {
        final ConfigurationSection regionProtectOnlyBreakAllowSection = wgrpPlugin.getWgrpPaperBase().getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtectOnlyBreakAllow");
        if (regionProtectOnlyBreakAllowSection != null) {
            try {
                for (String world : regionProtectOnlyBreakAllowSection.getKeys(false)) {
                    regionProtectOnlyBreakAllow.put(
                            world,
                            wgrpPlugin.getWgrpPaperBase().getConfig().getStringList("wgRegionProtect.regionProtectOnlyBreakAllow." + world)
                    );
                }
            } catch (Throwable ignored) {}
        }
        for (World w : Bukkit.getWorlds()) {
            final ArrayList<String> list = new ArrayList<>();
            if (!regionProtectOnlyBreakAllow.containsKey(w.getName())) {
                regionProtectOnlyBreakAllow.put(w.getName(), list);
            }
        }
    }

    private void regionProtectAllowSection() {
        final ConfigurationSection regionProtectAllowSection = wgrpPlugin.getWgrpPaperBase().getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtectAllow");
        if (regionProtectAllowSection != null) {
            try {
                for (String world : regionProtectAllowSection.getKeys(false)) {
                    regionProtectAllow.put(
                            world,
                            wgrpPlugin.getWgrpPaperBase().getConfig().getStringList("wgRegionProtect.regionProtectAllow." + world)
                    );
                }
            } catch (Throwable ignored) {}
        }
        for (World w : Bukkit.getWorlds()) {
            final ArrayList<String> list = new ArrayList<>();
            if (!regionProtectAllow.containsKey(w.getName())) {
                regionProtectAllow.put(w.getName(), list);
            }
        }
    }

    private void regionProtectSection() {
        final ConfigurationSection regionProtectSection = wgrpPlugin.getWgrpPaperBase().getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtect");
        if (regionProtectSection != null) {
            try {
                for (String world : regionProtectSection.getKeys(false)) {
                    regionProtect.put(world, wgrpPlugin.getWgrpPaperBase().getConfig().getStringList("wgRegionProtect.regionProtect." + world));
                }
            } catch (Throwable ignored) {}
        }
        for (World w : Bukkit.getWorlds()) {
            final ArrayList<String> list = new ArrayList<>();
            if (!regionProtect.containsKey(w.getName())) {
                regionProtect.put(w.getName(), list);
            }
        }
    }

    public Map<String, List<String>> getRegionProtectMap() {
        return regionProtect;
    }

    public void setRegionProtectMap(@NotNull Map<String, List<String>> value) {
        regionProtect = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpPlugin));
        }
    }

    public Map<String, List<String>> getRegionProtectAllowMap() {
        return regionProtectAllow;
    }

    public void setRegionProtectAllowMap(@NotNull Map<String, List<String>> value) {
        regionProtectAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpPlugin));
        }
    }

    public Map<String, List<String>> getRegionProtectOnlyBreakAllowMap() {
        return regionProtectOnlyBreakAllow;
    }

    public void setRegionProtectOnlyBreakAllow(@NotNull Map<String, List<String>> value) {
        regionProtectOnlyBreakAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpPlugin));
        }
    }

    public void saveConfig(final String path, final Object field) {
        try {
            if (regionProtect.isEmpty()) {
                wgrpPlugin.getWgrpPaperBase().getConfig().set(
                        "wgRegionProtect.regionProtect",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtect.entrySet()) {
                wgrpPlugin.getWgrpPaperBase().getConfig().set(
                        "wgRegionProtect.regionProtect." + entry.getKey(),
                        entry.getValue()
                );
            }
            if (regionProtectAllow.isEmpty()) {
                wgrpPlugin.getWgrpPaperBase().getConfig().set(
                        "wgRegionProtect.regionProtectAllow",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtectAllow.entrySet()) {
                wgrpPlugin.getWgrpPaperBase().getConfig().set(
                        "wgRegionProtect.regionProtectAllow." + entry.getKey(),
                        entry.getValue()
                );
            }
            if (regionProtectOnlyBreakAllow.isEmpty()) {
                wgrpPlugin.getWgrpPaperBase().getConfig().set(
                        "wgRegionProtect.regionProtectOnlyBreakAllow",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtectOnlyBreakAllow.entrySet()) {
                wgrpPlugin.getWgrpPaperBase().getConfig().set("wgRegionProtect.regionProtectOnlyBreakAllow." + entry.getKey(), entry.getValue());
            }
            wgrpPlugin.getWgrpPaperBase().getConfig().set(path, field);
            wgrpPlugin.getWgrpPaperBase().saveConfig();
        } catch (Exception e) {
            wgrpPlugin.getLogger().severe("Could not save config.yml! Error: " + e.getMessage());
            e.fillInStackTrace();
        }
    }

}