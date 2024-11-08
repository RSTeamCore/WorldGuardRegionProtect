package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperBase;
import net.ritasister.wgrp.rslibs.annotation.CanRecover;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private final WorldGuardRegionProtectPaperBase wgrpBase;

    @CanRecover
    private Map<String, List<String>> regionProtect;

    @CanRecover
    private Map<String, List<String>> regionProtectAllow;

    @CanRecover
    private Map<String, List<String>> regionProtectOnlyBreakAllow;


    public Config(WorldGuardRegionProtectPaperBase wgrpBase) {
        this.wgrpBase = wgrpBase;
        reloadConfig();
    }

    public void reloadConfig() {
        regionProtect = new HashMap<>();
        regionProtectAllow = new HashMap<>();
        regionProtectOnlyBreakAllow = new HashMap<>();

        wgrpBase.saveDefaultConfig();
        wgrpBase.reloadConfig();

        for (ConfigFields configFields : ConfigFields.values()) {
            try {
                configFields.get(wgrpBase);
                //start getting regions.
                regionProtectSection();
                regionProtectAllowSection();
                regionProtectOnlyBreakAllowSection();
                //End getting regions
            } catch (Exception e) {
                wgrpBase.getLogger().severe("Could not load config.yml! Error: " + e.getLocalizedMessage());
                e.fillInStackTrace();
            }
            for (Field field : ConfigFields.class.getFields()) {
                if (field.isAnnotationPresent(CanRecover.class)) {
                    try {
                        if (field.get(ConfigFields.class).equals(null)) {
                            if (field.getName().equals(configFields.name())) {
                                configFields.get(wgrpBase);
                                wgrpBase.getLogger().info(String.format("Field %s has been recovered", configFields.name()));
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            saveConfig(configFields.getPath(), configFields.get(wgrpBase));
        }
    }

    private void regionProtectOnlyBreakAllowSection() {
        final ConfigurationSection regionProtectOnlyBreakAllowSection = wgrpBase.getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtectOnlyBreakAllow");
        if (regionProtectOnlyBreakAllowSection != null) {
            try {
                for (String world : regionProtectOnlyBreakAllowSection.getKeys(false)) {
                    regionProtectOnlyBreakAllow.put(
                            world,
                            wgrpBase.getConfig().getStringList("wgRegionProtect.regionProtectOnlyBreakAllow." + world)
                    );
                }
            } catch (Throwable ignored) {
            }
        }
        for (World w : Bukkit.getWorlds()) {
            final ArrayList<String> list = new ArrayList<>();
            if (!regionProtectOnlyBreakAllow.containsKey(w.getName())) {
                regionProtectOnlyBreakAllow.put(w.getName(), list);
            }
        }
    }

    private void regionProtectAllowSection() {
        final ConfigurationSection regionProtectAllowSection = wgrpBase.getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtectAllow");
        if (regionProtectAllowSection != null) {
            try {
                for (String world : regionProtectAllowSection.getKeys(false)) {
                    regionProtectAllow.put(
                            world,
                            wgrpBase.getConfig().getStringList("wgRegionProtect.regionProtectAllow." + world)
                    );
                }
            } catch (Throwable ignored) {
            }
        }
        for (World w : Bukkit.getWorlds()) {
            final ArrayList<String> list = new ArrayList<>();
            if (!regionProtectAllow.containsKey(w.getName())) {
                regionProtectAllow.put(w.getName(), list);
            }
        }
    }

    private void regionProtectSection() {
        final ConfigurationSection regionProtectSection = wgrpBase.getConfig().getConfigurationSection(
                "wgRegionProtect.regionProtect");
        if (regionProtectSection != null) {
            try {
                for (String world : regionProtectSection.getKeys(false)) {
                    regionProtect.put(world, wgrpBase.getConfig().getStringList("wgRegionProtect.regionProtect." + world));
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
            saveConfig(configFields.getPath(), configFields.get(wgrpBase));
        }
    }

    public Map<String, List<String>> getRegionProtectAllowMap() {
        return regionProtectAllow;
    }

    public void setRegionProtectAllowMap(@NotNull Map<String, List<String>> value) {
        regionProtectAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpBase));
        }
    }

    public Map<String, List<String>> getRegionProtectOnlyBreakAllowMap() {
        return regionProtectOnlyBreakAllow;
    }

    public void setRegionProtectOnlyBreakAllow(@NotNull Map<String, List<String>> value) {
        regionProtectOnlyBreakAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.get(wgrpBase));
        }
    }

    /*public MySQLSettings getMySQLSettings() {
        return mysqlsettings;
    }*/

    public void saveConfig(final String path, final Object field) {
        try {
            if (regionProtect.isEmpty()) {
                wgrpBase.getConfig().set(
                        "wgRegionProtect.regionProtect",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtect.entrySet()) {
                wgrpBase.getConfig().set(
                        "wgRegionProtect.regionProtect." + entry.getKey(),
                        entry.getValue()
                );
            }
            if (regionProtectAllow.isEmpty()) {
                wgrpBase.getConfig().set(
                        "wgRegionProtect.regionProtectAllow",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtectAllow.entrySet()) {
                wgrpBase.getConfig().set(
                        "wgRegionProtect.regionProtectAllow." + entry.getKey(),
                        entry.getValue()
                );
            }
            if (regionProtectOnlyBreakAllow.isEmpty()) {
                wgrpBase.getConfig().set(
                        "wgRegionProtect.regionProtectOnlyBreakAllow",
                        new ArrayList<>()
                );
            }
            for (Map.Entry<String, List<String>> entry : regionProtectOnlyBreakAllow.entrySet()) {
                wgrpBase.getConfig().set("wgRegionProtect.regionProtectOnlyBreakAllow." + entry.getKey(), entry.getValue());
            }
            wgrpBase.getConfig().set(path, field);
            wgrpBase.saveConfig();
        } catch (Exception e) {
            wgrpBase.getLogger().severe("Could not save config.yml! Error: " + e.getLocalizedMessage());
            e.fillInStackTrace();
        }
    }
}
