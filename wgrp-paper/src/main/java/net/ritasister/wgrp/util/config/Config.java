package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
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

    private final WorldGuardRegionProtectBukkitBase wgrpBase;

    @CanRecover
    private Map<String, List<String>> regionProtect;

    @CanRecover
    private Map<String, List<String>> regionProtectAllow;

    @CanRecover
    private Map<String, List<String>> regionProtectOnlyBreakAllow;

    @CanRecover
    private String configVersion;

    @CanRecover
    private String lang;

    @CanRecover
    private boolean updateChecker;

    @CanRecover
    private boolean sendNoUpdate;

    @CanRecover
    private List<String> interactType;

    @CanRecover
    private List<String> vehicleType;

    @CanRecover
    private List<String> animalType;

    @CanRecover
    private List<String> monsterType;

    @CanRecover
    private List<String> waterMobType;

    @CanRecover
    private List<String> entityExplodeType;

    @CanRecover
    private List<String> naturalBlockOrItem;

    @CanRecover
    private List<String> signType;

    @CanRecover
    private boolean denyCollisionWithVehicle;

    @CanRecover
    private boolean denySitAsPassengerInVehicle;

    @CanRecover
    private boolean denyDamageVehicle;

    @CanRecover
    private boolean denyTakeLecternBook;

    @CanRecover
    private boolean denyWaterFlowToRegion;

    @CanRecover
    private boolean denyLavaFlowToRegion;

    @CanRecover
    private boolean denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot;

    @CanRecover
    private boolean denyInteractWithItemFrame;

    @CanRecover
    private boolean denyPlaceItemFrameOrPainting;

    @CanRecover
    private boolean denyDamageItemFrameOrPainting;

    @CanRecover
    private boolean denyStonecutterRecipeSelect;

    @CanRecover
    private boolean denyLoomPatternSelect;

    @CanRecover
    private List<String> spyCommandList;

    @CanRecover
    private List<String> cmdWe;

    @CanRecover
    private List<String> cmdWeC;

    @CanRecover
    private List<String> cmdWeP;

    @CanRecover
    private List<String> cmdWeS;

    @CanRecover
    private List<String> cmdWeU;

    @CanRecover
    private List<String> cmdWeCP;

    @CanRecover
    private boolean regionMessageProtect;

    @CanRecover
    private boolean regionMessageProtectWe;

    @CanRecover
    private boolean isSpyCommandNotifyConsoleEnable;

    @CanRecover
    private boolean isSpyCommandNotifyAdminEnable;

    @CanRecover
    private boolean explodeEntity;

    @CanRecover
    private String spyCommandNotifyAdminPlaySound;

    @CanRecover
    private boolean spyCommandNotifyAdminPlaySoundEnable;

    @CanRecover
    private boolean databaseEnable;

    //private MySQLSettings mysqlsettings;

    public Config(WorldGuardRegionProtectBukkitBase wgrpBase) {
        this.wgrpBase = wgrpBase;
        reloadConfig();
    }

    public void reloadConfig() {
        for (ConfigFields configFields : ConfigFields.values()) {
            regionProtect = new HashMap<>();
            regionProtectAllow = new HashMap<>();
            regionProtectOnlyBreakAllow = new HashMap<>();

            wgrpBase.saveDefaultConfig();
            wgrpBase.reloadConfig();

            try {
                wgrpBase.getConfig().get(configFields.getPath());

                //start getting regions.
                regionProtectSection();
                regionProtectAllowSection();
                regionProtectOnlyBreakAllowSection();
                //End getting regions
            } catch (Exception e) {
                wgrpBase.getApi().getPluginLogger().severe("Could not load config.yml! Error: " + e.getLocalizedMessage());
                e.fillInStackTrace();
            }
            for (Field field : this.getClass().getFields()) {
                if (field.isAnnotationPresent(CanRecover.class)) {
                    try {
                        if (field.get(this.getClass()) == null) {
                            if (field.getName().equals(configFields.name())) {
                                wgrpBase.getApi().getPluginLogger().info("Fields will be loaded: " + configFields.name());
                            }
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            saveConfig(configFields.getPath(), configFields.getParam());
        }
    }

    private void getVariables() {
        lang = wgrpBase.getConfig().getString("wgRegionProtect.lang");
        updateChecker = wgrpBase.getConfig().getBoolean("wgRegionProtect.updateChecker.enable");
        sendNoUpdate = wgrpBase.getConfig().getBoolean("wgRegionProtect.updateChecker.sendNoUpdate");

        //start getting regions.
        regionProtectSection();
        regionProtectAllowSection();
        regionProtectOnlyBreakAllowSection();
        //End getting regions

        interactType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.interactType");
        vehicleType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.vehicleType");
        animalType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.animalType");
        monsterType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.monsterType");
        waterMobType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.waterMobType");
        signType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.signType");
        entityExplodeType = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.entityExplodeType");
        naturalBlockOrItem = wgrpBase.getConfig().getStringList("wgRegionProtect.protectInteract.naturalBlockOrItem");
        denyCollisionWithVehicle = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.vehicle.denyCollisionWithVehicle");
        denySitAsPassengerInVehicle = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.vehicle.denySitAsPassengerInVehicle");
        denyDamageVehicle = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.vehicle.denyDamageVehicle");
        denyTakeLecternBook = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.tools.denyTakeLecternBook");
        denyStonecutterRecipeSelect = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.tools.denyStonecutterRecipeSelect");
        denyLoomPatternSelect = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.tools.denyLoomPatternSelect");
        denyPlaceItemFrameOrPainting = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.itemFrame.denyPlaceItemFrameOrPainting");
        denyInteractWithItemFrame = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.itemFrame.denyInteractWithItemFrame");
        denyDamageItemFrameOrPainting = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.itemFrame.denyDamageItemFrameOrPainting");
        denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.player.misc.denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot");
        denyWaterFlowToRegion = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.other.denyWaterFlowToRegion");
        denyLavaFlowToRegion = wgrpBase.getConfig().getBoolean("wgRegionProtect.protectInteract.other.denyLavaFlowToRegion");

        cmdWe = wgrpBase.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWe");
        cmdWeC = wgrpBase.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeC");
        cmdWeP = wgrpBase.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeP");
        cmdWeS = wgrpBase.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeS");
        cmdWeU = wgrpBase.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeU");
        cmdWeCP = wgrpBase.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeCP");

        explodeEntity = wgrpBase.getConfig().getBoolean("wgRegionProtect.explodeEntity.enable");

        regionMessageProtect = wgrpBase.getConfig().getBoolean("wgRegionProtect.regionMessageProtect");
        regionMessageProtectWe = wgrpBase.getConfig().getBoolean("wgRegionProtect.regionMessageProtectWe");

        isSpyCommandNotifyConsoleEnable = wgrpBase.getConfig().getBoolean(
                "wgRegionProtect.spySettings.notify.console.enable");
        isSpyCommandNotifyAdminEnable = wgrpBase.getConfig().getBoolean(
                "wgRegionProtect.spySettings.notify.admin.enable");
        spyCommandNotifyAdminPlaySoundEnable = wgrpBase.getConfig().getBoolean(
                "wgRegionProtect.spySettings.notify.sound.enable");
        spyCommandNotifyAdminPlaySound = wgrpBase.getConfig().getString(
                "wgRegionProtect.spySettings.notify.sound.type");
        spyCommandList = wgrpBase.getConfig().getStringList("wgRegionProtect.spySettings.spyCommandList");

        //Database settings.
        databaseEnable = wgrpBase.getConfig().getBoolean("wgRegionProtect.dataSource.enable");
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

    public String getConfigVersion() {
        return configVersion;
    }

    public String getLang() {
        return lang;
    }

    public boolean isUpdateChecker() {
        return updateChecker;
    }

    public boolean isSendNoUpdate() {
        return sendNoUpdate;
    }

    public Map<String, List<String>> getRegionProtectMap() {
        return regionProtect;
    }

    public void setRegionProtectMap(@NotNull Map<String, List<String>> value) {
        regionProtect = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.getParam());
        }
    }

    public Map<String, List<String>> getRegionProtectAllowMap() {
        return regionProtectAllow;
    }

    public void setRegionProtectAllowMap(@NotNull Map<String, List<String>> value) {
        regionProtectAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.getParam());
        }
    }

    public Map<String, List<String>> getRegionProtectOnlyBreakAllowMap() {
        return regionProtectOnlyBreakAllow;
    }

    public void setRegionProtectOnlyBreakAllow(@NotNull Map<String, List<String>> value) {
        regionProtectOnlyBreakAllow = value;
        for (ConfigFields configFields : ConfigFields.values()) {
            saveConfig(configFields.getPath(), configFields.getParam());
        }
    }

    public List<String> getInteractType() {
        return interactType;
    }

    public List<String> getVehicleType() {
        return vehicleType;
    }

    public List<String> getAnimalType() {
        return animalType;
    }

    public List<String> getMonsterType() {
        return monsterType;
    }

    public List<String> getWaterMobType() {
        return waterMobType;
    }

    public List<String> getEntityExplodeType() {
        return entityExplodeType;
    }

    public List<String> getSignType() {
        return signType;
    }

    public List<String> getNaturalBlockOrItem() {
        return naturalBlockOrItem;
    }

    public boolean isDenyCollisionWithVehicle() {
        return denyCollisionWithVehicle;
    }

    public boolean isDenySitAsPassengerInVehicle() {
        return denySitAsPassengerInVehicle;
    }

    public boolean isDenyDamageVehicle() {
        return denyDamageVehicle;
    }

    public boolean isDenyTakeLecternBook() {
        return denyTakeLecternBook;
    }

    public boolean isDenyTakeOrPlaceNaturalBlockOrItemIOFlowerPot() {
        return denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot;
    }

    public boolean isDenyPlaceItemFrameOrPainting() {
        return denyPlaceItemFrameOrPainting;
    }

    public boolean isDenyInteractWithItemFrame() {
        return denyInteractWithItemFrame;
    }

    public boolean isDenyDamageItemFrameOrPainting() {
        return denyDamageItemFrameOrPainting;
    }

    public boolean isDenyStonecutterRecipeSelect() {
        return denyStonecutterRecipeSelect;
    }

    public boolean isDenyLoomPatternSelect() {
        return denyLoomPatternSelect;
    }

    public boolean isDenyWaterFlowToRegion() {
        return denyWaterFlowToRegion;
    }

    public boolean isDenyLavaFlowToRegion() {
        return denyLavaFlowToRegion;
    }

    public List<String> getSpyCommandList() {
        return spyCommandList;
    }

    public List<String> getCmdWe() {
        return cmdWe;
    }

    public List<String> getCmdWeC() {
        return cmdWeC;
    }

    public List<String> getCmdWeP() {
        return cmdWeP;
    }

    public List<String> getCmdWeS() {
        return cmdWeS;
    }

    public List<String> getCmdWeU() {
        return cmdWeU;
    }

    public List<String> getCmdWeCP() {
        return cmdWeCP;
    }

    public boolean getRegionMessageProtect() {
        return regionMessageProtect;
    }

    public boolean getRegionMessageProtectWe() {
        return regionMessageProtectWe;
    }

    public boolean getSpyCommandNotifyConsoleEnable() {
        return isSpyCommandNotifyConsoleEnable;
    }

    public boolean getSpyCommandNotifyAdminEnable() {
        return isSpyCommandNotifyAdminEnable;
    }

    public boolean getExplodeEntity() {
        return explodeEntity;
    }

    public String getSpyCommandNotifyAdminPlaySound() {
        return spyCommandNotifyAdminPlaySound;
    }

    public boolean getSpyCommandNotifyAdminPlaySoundEnable() {
        return spyCommandNotifyAdminPlaySoundEnable;
    }

    public boolean getDataBaseEnable() {
        return databaseEnable;
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
            wgrpBase.getApi().getPluginLogger().severe("Could not save config.yml! Error: " + e.getLocalizedMessage());
            e.fillInStackTrace();
        }
    }

    /*public static class MySQLSettings {

        private final String host;
        private final int port;
        private final String database;
        private final String user;
        private final String password;
        private final String table;
        private final int maxPoolSize;
        private final int maxLifetime;
        private final int connectionTimeout;
        private final boolean useSsl;
        private final int intervalReload;

        MySQLSettings(String host, int port, String database, String user, String password, String table, int maxPoolSize, int maxLifetime, int connectionTimeout, boolean useSsl, int intervalReload) {
            this.host = host;
            this.port = port;
            this.database = database;
            this.user = user;
            this.password = password;
            this.table = table;
            this.maxPoolSize = maxPoolSize;
            this.maxLifetime = maxLifetime;
            this.connectionTimeout = connectionTimeout;
            this.useSsl = useSsl;
            this.intervalReload = intervalReload;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getDataBase() {
            return database;
        }

        public String getUser() {
            return user;
        }

        public String getPassword() {
            return password;
        }

        public String getTable() {
            return table;
        }

        public int getMaxLifetime() {
            return maxLifetime;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public boolean getUseSsl() {
            return useSsl;
        }

        public int getIntervalReload() {
            return intervalReload;
        }

    }*/

}
