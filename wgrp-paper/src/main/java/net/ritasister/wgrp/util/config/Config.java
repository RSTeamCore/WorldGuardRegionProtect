package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

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

        for (ConfigFields configFields : ConfigFields.values()) {
            try {
                configFields.get(wgrpPaperBase);
                //start getting regions.
                regionProtectSection();
                regionProtectAllowSection();
                regionProtectOnlyBreakAllowSection();
                //End getting regions
            } catch (Exception e) {
                wgrpPaperBase.getLogger().severe("Could not load config.yml! Error: " + e.getLocalizedMessage());
                e.fillInStackTrace();
            }
            saveConfig(configFields.getPath(), configFields.get(wgrpPaperBase));
        }
    }

    private void regionProtectOnlyBreakAllowSection() {
        final ConfigurationSection regionProtectOnlyBreakAllowSection = wgrpPaperBase.getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtectOnlyBreakAllow");
        if (regionProtectOnlyBreakAllowSection != null) {
            try {
                for (String world : regionProtectOnlyBreakAllowSection.getKeys(false)) {
                    regionProtectOnlyBreakAllow.put(
                            world,
                            wgrpPaperBase.getConfig().getStringList("wgRegionProtect.regionProtectOnlyBreakAllow." + world)
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
        final ConfigurationSection regionProtectAllowSection = wgrpPaperBase.getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtectAllow");
        if (regionProtectAllowSection != null) {
            try {
                for (String world : regionProtectAllowSection.getKeys(false)) {
                    regionProtectAllow.put(
                            world,
                            wgrpPaperBase.getConfig().getStringList("wgRegionProtect.regionProtectAllow." + world)
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
        final ConfigurationSection regionProtectSection = wgrpPaperBase.getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtect");
        if (regionProtectSection != null) {
            try {
                for (String world : regionProtectSection.getKeys(false)) {
                    regionProtect.put(world, wgrpPaperBase.getConfig().getStringList("wgRegionProtect.regionProtect." + world));
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
            saveConfig(configFields.getPath(), configFields.get(wgrpPaperBase));
        }
    }

    public Map<String, List<String>> getRegionProtectAllowMap() {
        return regionProtectAllow;
    }

    public void setRegionProtectAllowMap(@NotNull Map<String, List<String>> value) {
        regionProtectAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpPaperBase));
        }
    }

    public Map<String, List<String>> getRegionProtectOnlyBreakAllowMap() {
        return regionProtectOnlyBreakAllow;
    }

    public void setRegionProtectOnlyBreakAllow(@NotNull Map<String, List<String>> value) {
        regionProtectOnlyBreakAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpPaperBase));
        }
    }

    public void saveConfig(final String path, final Object field) {
        try {
            if (regionProtect.isEmpty()) {
                wgrpPaperBase.getConfig().set(
                        "wgRegionProtect.regionProtect",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtect.entrySet()) {
                wgrpPaperBase.getConfig().set(
                        "wgRegionProtect.regionProtect." + entry.getKey(),
                        entry.getValue()
                );
            }
            if (regionProtectAllow.isEmpty()) {
                wgrpPaperBase.getConfig().set(
                        "wgRegionProtect.regionProtectAllow",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtectAllow.entrySet()) {
                wgrpPaperBase.getConfig().set(
                        "wgRegionProtect.regionProtectAllow." + entry.getKey(),
                        entry.getValue()
                );
            }
            if (regionProtectOnlyBreakAllow.isEmpty()) {
                wgrpPaperBase.getConfig().set(
                        "wgRegionProtect.regionProtectOnlyBreakAllow",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtectOnlyBreakAllow.entrySet()) {
                wgrpPaperBase.getConfig().set("wgRegionProtect.regionProtectOnlyBreakAllow." + entry.getKey(), entry.getValue());
            }
            wgrpPaperBase.getConfig().set(path, field);
            wgrpPaperBase.saveConfig();
        } catch (Exception e) {
            wgrpPaperBase.getLogger().severe("Could not save config.yml! Error: " + e.getLocalizedMessage());
            e.fillInStackTrace();
        }
    }

}
