package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private final WorldGuardRegionProtect wgRegionProtect;

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    @CanRecover
    private HashMap<String, List<String>> regionProtect;

    @CanRecover
    private HashMap<String, List<String>> regionProtectAllow;

    @CanRecover
    private HashMap<String, List<String>> regionProtectOnlyBreakAllow;

    @CanRecover
    private String lang;

    @CanRecover
    private boolean updateChecker;
    @CanRecover
    private List<String> interactType;

    @CanRecover
    private boolean collisionWithVehicle;

    @CanRecover
    private boolean canSitAsPassengerInVehicle;

    @CanRecover
    private boolean canDamageVehicle;

    @CanRecover
    private boolean canTakeLecternBook;

    @CanRecover
    private boolean denyWaterFlowToRegion;

    @CanRecover
    private boolean denyLavaFlowToRegion;

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
    private boolean spyCommandNotifyConsole;

    @CanRecover
    private boolean spyCommandNotifyAdmin;

    @CanRecover
    private boolean explodeEntity;

    @CanRecover
    private String spyCommandNotifyAdminPlaySound;

    @CanRecover
    private boolean spyCommandNotifyAdminPlaySoundEnable;

    @CanRecover
    private boolean databaseEnable;

    private MySQLSettings mysqlsettings;

    public Config(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin wgrpBukkitPlugin) {
        this.wgRegionProtect=wgRegionProtect;
        this.wgrpBukkitPlugin=wgrpBukkitPlugin;
        reload();
    }

    @SuppressWarnings("unchecked")
    public void reload() {
        regionProtect = new HashMap<>();
        regionProtectAllow = new HashMap<>();
        regionProtectOnlyBreakAllow = new HashMap<>();

        wgRegionProtect.getWGRPBukkitPlugin().saveDefaultConfig();
        wgRegionProtect.getWGRPBukkitPlugin().reloadConfig();

        try {
            lang = wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.lang");
            updateChecker = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.updateChecker");

            //start getting regions.
            ConfigurationSection regionProtectSection = wgrpBukkitPlugin.getConfig().getConfigurationSection("wgRegionProtect.regionProtect");
            if (regionProtectSection != null) {
                try {
                    for (String world : regionProtectSection.getKeys(false)) {
                        regionProtect.put(world, wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.regionProtect." + world));
                    }
                } catch (Throwable ignored) {}
            }
            for(World w : Bukkit.getWorlds()) {
                ArrayList<String> l = new ArrayList<>();
                if(!regionProtect.containsKey(w.getName())) {
                    regionProtect.put(w.getName(), l);
                }
            }

            ConfigurationSection regionProtectAllowSection = wgrpBukkitPlugin.getConfig().getConfigurationSection("wgRegionProtect.regionProtectAllow");
            if (regionProtectAllowSection != null) {
                try {
                    for (String world : regionProtectAllowSection.getKeys(false)) {
                        regionProtectAllow.put(world, wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.regionProtectAllow." + world));
                    }
                } catch (Throwable ignored) {}
            }
            for(World w : Bukkit.getWorlds()) {
                ArrayList<String> l = new ArrayList<>();
                if(!regionProtectAllow.containsKey(w.getName())) {
                    regionProtectAllow.put(w.getName(), l);
                }
            }

            ConfigurationSection regionProtectOnlyBreakAllowSection = wgrpBukkitPlugin.getConfig().getConfigurationSection("wgRegionProtect.regionProtectOnlyBreakAllow");
            if (regionProtectOnlyBreakAllowSection != null) {
                try {
                    for (String world : regionProtectOnlyBreakAllowSection.getKeys(false)) {
                        regionProtectOnlyBreakAllow.put(world, wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.regionProtectOnlyBreakAllow." + world));
                    }
                }  catch (Throwable ignored) {}
            }
            for(World w : Bukkit.getWorlds()) {
                ArrayList<String> l = new ArrayList<>();
                if(!regionProtectOnlyBreakAllow.containsKey(w.getName())) {
                    regionProtectOnlyBreakAllow.put(w.getName(), l);
                }
            }
            //End getting regions

            interactType = (List<String>)wgrpBukkitPlugin.getConfig().getList("wgRegionProtect.protectInteract.interactType");
            collisionWithVehicle = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.collisionWithVehicle");
            canSitAsPassengerInVehicle = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.canSitAsPassengerInVehicle");
            canDamageVehicle = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.canDamageVehicle");
            canTakeLecternBook = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.canTakeLecternBook");
            denyWaterFlowToRegion = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.protectInteract.other.denyWaterFlowToRegion");
            denyLavaFlowToRegion = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.protectInteract.other.denyLavaFlowToRegion");

            cmdWe = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWe");
            cmdWeC = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeC");
            cmdWeP = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeP");
            cmdWeS = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeS");
            cmdWeU = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeU");
            cmdWeCP = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeCP");

            explodeEntity = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.explodeEntity.enable");

            regionMessageProtect = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.regionMessageProtect");
            regionMessageProtectWe = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.regionMessageProtectWe");

            spyCommandNotifyConsole = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.spySettings.notify.console.enable");
            spyCommandNotifyAdmin = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.spySettings.notify.admin.enable");
            spyCommandNotifyAdminPlaySoundEnable = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.spySettings.notify.sound.enable");
            spyCommandNotifyAdminPlaySound = wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.spySettings.notify.sound.type");
            spyCommandList = wgrpBukkitPlugin.getConfig().getStringList("wgRegionProtect.spySettings.spyCommandList");

            //Database settings.
            databaseEnable = wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.dataSource.enable");
            mysqlsettings = new MySQLSettings(
                    wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.dataSource.host"),
                    wgrpBukkitPlugin.getConfig().getInt("wgRegionProtect.dataSource.port"),
                    wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.dataSource.database"),
                    wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.dataSource.user"),
                    wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.dataSource.password"),
                    wgrpBukkitPlugin.getConfig().getString("wgRegionProtect.dataSource.table"),
                    wgrpBukkitPlugin.getConfig().getInt("wgRegionProtect.dataSource.maxPoolSize"),
                    wgrpBukkitPlugin.getConfig().getInt("wgRegionProtect.dataSource.maxLifetime"),
                    wgrpBukkitPlugin.getConfig().getInt("wgRegionProtect.dataSource.connectionTimeout"),
                    wgrpBukkitPlugin.getConfig().getBoolean("wgRegionProtect.dataSource.useSsl"),
                    wgrpBukkitPlugin.getConfig().getInt("wgRegionProtect.dataSource.intervalReload")
            );

        } catch (Exception e) {
            wgRegionProtect.getWGRPBukkitPlugin().getLogger().severe("Could not load config.yml! Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        for(Field f : this.getClass().getFields()) {
            if(f.isAnnotationPresent(CanRecover.class)) {
                try {
                    if(f.get(this.getClass()).equals(null)) {
                        switch (f.getName()) {
                            case "lang" -> lang = "en";
                            case "updateChecker" -> updateChecker = true;

                            case "interactType" -> interactType = List.of(
                                    "armor_stand", "end_crystal", "minecart",
                                    "explosive_minecart", "command_minecart", "hopper_minecart",
                                    "storage_minecart", "powered_minecart", "boat",
                                    "boat_spruce", "boat_birch", "boat_jungle",
                                    "boat_acacia", "boat_dark_oak", "bucket",
                                    "water_bucket", "lava_bucket");
                            case "collisionWithVehicle" -> collisionWithVehicle = true;
                            case "canSitAsPassengerInVehicle" -> canSitAsPassengerInVehicle = true;
                            case "canDamageVehicle" -> canDamageVehicle = true;
                            case "canTakeLecternBook" -> canTakeLecternBook = true;

                            case "cmdWe" -> cmdWe = List.of(
                                    "//set", "//replace", "//overlay",
                                    "//walls", "//deform", "//fill",
                                    "//fillr", "//fixlava", "//hollow",
                                    "//move", "//stack", "//smooth",
                                    "//cut", "//replacenear");
                            case "cmdWeC" -> cmdWeC = List.of(
                                    "//cyl", "//hcyl", "//drain", "//rep");
                            case "cmdWeP" -> cmdWeP = List.of(
                                    "//pyramid", "//hpyramid");
                            case "cmdWeS" -> cmdWeS = List.of(
                                    "//sphere", "//hsphere");
                            case "cmdWeU" -> cmdWeU = List.of(
                                    "//up", "/up");
                            case "cmdWeCP" -> cmdWeCP = List.of(
                                    "//paste", "//place", "//replacenear", "//hollow");

                            case "explodeEntity" -> explodeEntity = true;

                            case "regionMessageProtect" -> regionMessageProtect = true;
                            case "regionMessageProtectWe" -> regionMessageProtectWe = true;
                            case "spyCommandNotifyConsole" -> spyCommandNotifyConsole = true;
                            case "spyCommandNotifyAdmin" -> spyCommandNotifyAdmin = true;
                            case "spyCommandNotifyAdminPlaySoundEnable" -> spyCommandNotifyAdminPlaySoundEnable = true;
                            case "spyCommandNotifyAdminPlaySound" -> spyCommandNotifyAdminPlaySound = "BLOCK_ANVIL_PLACE";
                            case "spyCommandList" -> spyCommandList = List.of(
                                    "//set", "//replace", "//overlay",
                                    "//walls", "//deform", "//fill",
                                    "//fillr", "//fixlava", "//hollow",
                                    "//move", "//stack", "//smooth",
                                    "//cut", "//replacenear");

                            //Database settings.
                            case "enable" -> databaseEnable = false;
                            case "mysqlsettings" -> mysqlsettings = new MySQLSettings(
                                    "localhost",
                                    3306,
                                    "database",
                                    "root",
                                    "password",
                                    "wgrp_logs",
                                    10,
                                    1800,
                                    5000,
                                    true,
                                    60);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        saveConfig();
    }

    public String getLang() {
        return lang;
    }

    public boolean getUpdateChecker() { return updateChecker; }

    public HashMap<String, List<String>> getRegionProtectMap() {
        return regionProtect;
    }

    public HashMap<String, List<String>> getRegionProtectAllowMap() {
        return regionProtectAllow;
    }

    public HashMap<String, List<String>> getRegionProtectOnlyBreakAllowMap() {
        return regionProtectOnlyBreakAllow;
    }

    public void setRegionProtectMap(@NotNull HashMap<String, List<String>> value) {
        regionProtect = value;
        saveConfig();
    }

    public void setRegionProtectAllowMap(@NotNull HashMap<String, List<String>> value) {
        regionProtectAllow = value;
        saveConfig();
    }

    public void setRegionProtectOnlyBreakAllow(@NotNull HashMap<String, List<String>> value) {
        regionProtectOnlyBreakAllow = value;
        saveConfig();
    }

    public List<String> getInteractType() {
        return interactType;
    }

    public boolean getCollisionWithVehicle() {
        return collisionWithVehicle;
    }

    public boolean getCanSitAsPassengerInVehicle() {
        return canSitAsPassengerInVehicle;
    }

    public boolean getCanDamageVehicle() {
        return canDamageVehicle;
    }

    public boolean getCanTakeLecternBook() {
        return canTakeLecternBook;
    }


    public boolean isDenyWaterFlowToRegion() { return denyWaterFlowToRegion; }

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

    public boolean getSpyCommandNotifyConsole() {
        return spyCommandNotifyConsole;
    }

    public boolean getSpyCommandNotifyAdmin() {
        return spyCommandNotifyAdmin;
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

    public MySQLSettings getMySQLSettings() {
        return mysqlsettings;
    }

    public void saveConfig() {
        try {
            if(regionProtect.isEmpty()) wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionProtect", new ArrayList<>());
            for(Map.Entry<String, List<String>> entry : regionProtect.entrySet()) {
                wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionProtect." + entry.getKey(), entry.getValue());
            }
            if(regionProtectAllow.isEmpty()) wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionProtectAllow", new ArrayList<>());
            for(Map.Entry<String, List<String>> entry : regionProtectAllow.entrySet()) {
                wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionProtectAllow." + entry.getKey(), entry.getValue());
            }
            if(regionProtectOnlyBreakAllow.isEmpty()) wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionProtectOnlyBreakAllow", new ArrayList<>());
            for(Map.Entry<String, List<String>> entry : regionProtectOnlyBreakAllow.entrySet()) {
                wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionProtectOnlyBreakAllow." + entry.getKey(), entry.getValue());
            }

            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.interactType", interactType);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.collisionWithVehicle", collisionWithVehicle);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.canSitAsPassengerInVehicle", canSitAsPassengerInVehicle);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.canDamageVehicle", canDamageVehicle);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.canTakeLecternBook", canTakeLecternBook);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.other.denyWaterFlowToRegion", denyWaterFlowToRegion);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.other.denyLavaFlowToRegion", denyLavaFlowToRegion);

            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWe", cmdWe);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeC", cmdWeC);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeP", cmdWeP);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeS", cmdWeS);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeU", cmdWeU);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeCP", cmdWeCP);

            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.explodeEntity.enable", explodeEntity);

            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionMessageProtect", regionMessageProtect);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.regionMessageProtectWe", regionMessageProtectWe);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.console.enable", spyCommandNotifyConsole);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.admin.enable", spyCommandNotifyAdmin);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.sound.enable", spyCommandNotifyAdminPlaySoundEnable );
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.sound.type", spyCommandNotifyAdminPlaySound);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.spyCommandList", spyCommandList);

            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.enable", databaseEnable);
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.host", mysqlsettings.getHost());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.port", mysqlsettings.getPort());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.database", mysqlsettings.getDataBase());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.user", mysqlsettings.getUser());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.password", mysqlsettings.getPassword());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.table", mysqlsettings.getTable());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.maxPoolSize", mysqlsettings.getMaxPoolSize());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.maxLifetime", mysqlsettings.getMaxLifetime());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.connectionTimeout", mysqlsettings.getConnectionTimeout());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.useSsl", mysqlsettings.getUseSsl());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.intervalReload", mysqlsettings.getIntervalReload());
            wgRegionProtect.getWGRPBukkitPlugin().getConfig().set("wgRegionProtect.lang", lang);
            wgRegionProtect.getWGRPBukkitPlugin().saveConfig();
        } catch (Exception e) {
            wgRegionProtect.getWGRPBukkitPlugin().getLogger().severe("Could not save config.yml! Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public static class MySQLSettings {
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

        MySQLSettings(String host, int port, String database, String user, String password, String table,
                      int maxPoolSize, int maxLifetime, int connectionTimeout, boolean useSsl, int intervalReload) {
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
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface CanRecover { }
}