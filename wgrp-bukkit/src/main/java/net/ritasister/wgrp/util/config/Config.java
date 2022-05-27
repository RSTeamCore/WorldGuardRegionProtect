package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WGRPBukkitPlugin;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;

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
    private WGRPBukkitPlugin plugin;

    private HashMap<String, List<String>> regionsProtectMap; //<world, list of regions>

    private HashMap<String, List<String>> regionsProtectAllowMap; //<world, list of regions>

    private HashMap<String, List<String>> regionsOnlyBreakAllowMap; //<world, list of regions>

    @CanRecover
    private String lang;

    @CanRecover
    private ConfigurationSection regionProtect; //TODO

    @CanRecover
    private ConfigurationSection regionProtectAllow;

    @CanRecover
    private ConfigurationSection regionProtectOnlyBreakAllow;

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

    public Config(WorldGuardRegionProtect wgRegionProtect, WGRPBukkitPlugin plugin) {
        this.wgRegionProtect = wgRegionProtect;
        this.plugin = plugin;
        reload();
    }

    @SuppressWarnings("unchecked")
    public void reload() {
        wgRegionProtect.getWgrpBukkitPlugin().saveDefaultConfig();
        wgRegionProtect.getWgrpBukkitPlugin().reloadConfig();

        try {
            lang = plugin.getConfig().getString("wgRegionProtect.lang");

            regionProtect = plugin.getConfig().getConfigurationSection("wgRegionProtect.regionProtect");
            //Start getting regions
            regionsProtectMap = new HashMap<>();
            for(String world : regionProtect.getKeys(false)) {
                regionsProtectMap.put(world, plugin.getConfig().getStringList("wgRegionProtect.regionProtect." + world));
            }
            //End getting regions

            regionProtectAllow = plugin.getConfig().getConfigurationSection("wgRegionProtect.regionProtectAllow");
            //Start getting regions
            regionsProtectAllowMap = new HashMap<>();
            for(String world : regionProtectAllow.getKeys(false)) {
                regionsProtectAllowMap.put(world, plugin.getConfig().getStringList("wgRegionProtect.regionProtectAllow." + world));
            }
            //End getting regions

            regionProtectOnlyBreakAllow = plugin.getConfig().getConfigurationSection("wgRegionProtect.regionProtectOnlyBreakAllow");
            //Start getting regions
            regionsOnlyBreakAllowMap = new HashMap<>();
            for(String world : regionProtectOnlyBreakAllow.getKeys(false)) {
                regionsOnlyBreakAllowMap.put(world, plugin.getConfig().getStringList("wgRegionProtect.regionProtectOnlyBreakAllow." + world));
            }
            //End getting regions

            interactType = (List<String>)plugin.getConfig().getList("wgRegionProtect.protectInteract.interactType");
            collisionWithVehicle = plugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.collisionWithVehicle");
            canSitAsPassengerInVehicle = plugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.canSitAsPassengerInVehicle");
            canDamageVehicle = plugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.canDamageVehicle");
            canTakeLecternBook = plugin.getConfig().getBoolean("wgRegionProtect.protectInteract.player.canTakeLecternBook");

            cmdWe = plugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWe");
            cmdWeC = plugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeC");
            cmdWeP = plugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeP");
            cmdWeS = plugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeS");
            cmdWeU = plugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeU");
            cmdWeCP = plugin.getConfig().getStringList("wgRegionProtect.noProtectCmd.cmdWeCP");

            explodeEntity = plugin.getConfig().getBoolean("wgRegionProtect.explodeEntity.enable");

            regionMessageProtect = plugin.getConfig().getBoolean("wgRegionProtect.regionMessageProtect");
            regionMessageProtectWe = plugin.getConfig().getBoolean("wgRegionProtect.regionMessageProtectWe");

            spyCommandNotifyConsole = plugin.getConfig().getBoolean("wgRegionProtect.spySettings.notify.console.enable");
            spyCommandNotifyAdmin = plugin.getConfig().getBoolean("wgRegionProtect.spySettings.notify.admin.enable");
            spyCommandNotifyAdminPlaySoundEnable = plugin.getConfig().getBoolean("wgRegionProtect.spySettings.notify.sound.enable");
            spyCommandNotifyAdminPlaySound = plugin.getConfig().getString("wgRegionProtect.spySettings.notify.sound.type");
            spyCommandList = plugin.getConfig().getStringList("wgRegionProtect.spySettings.spyCommandList");

            //Database settings.
            databaseEnable = plugin.getConfig().getBoolean("wgRegionProtect.dataSource.databaseEnable");
            mysqlsettings = new MySQLSettings(
                    plugin.getConfig().getString("wgRegionProtect.dataSource.host"),
                    plugin.getConfig().getInt("wgRegionProtect.dataSource.port"),
                    plugin.getConfig().getString("wgRegionProtect.dataSource.name"),
                    plugin.getConfig().getString("wgRegionProtect.dataSource.user"),
                    plugin.getConfig().getString("wgRegionProtect.dataSource.password"),
                    plugin.getConfig().getString("wgRegionProtect.dataSource.table"),
                    plugin.getConfig().getInt("wgRegionProtect.dataSource.maxPoolSize"),
                    plugin.getConfig().getInt("wgRegionProtect.dataSource.maxLifetime"),
                    plugin.getConfig().getInt("wgRegionProtect.dataSource.connectionTimeout"),
                    plugin.getConfig().getInt("wgRegionProtect.dataSource.intervalReload")
            );
        } catch (Exception e) {
            wgRegionProtect.getWgrpBukkitPlugin().getLogger().severe("Could not load config.yml! Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }


        for(Field f : this.getClass().getFields()) {
            if(f.isAnnotationPresent(CanRecover.class)) {
                try {
                    if(f.get(this.getClass()).equals(null)) {
                        switch (f.getName()) {
                            case "lang" -> lang = "en";
                            case "regionProtect" -> {
                                ConfigurationSection section = new MemoryConfiguration();
                                ArrayList<String> exampleRegions = new ArrayList<>();
                                exampleRegions.add("spawn");
                                exampleRegions.add("pvp");
                                section.set("world", exampleRegions);
                                section.set("spawn", exampleRegions);
                                regionProtect = section;
                            }
                            case "regionProtectAllow"  -> {
                                ConfigurationSection section = new MemoryConfiguration();
                                ArrayList<String> exampleRegions = new ArrayList<>();
                                exampleRegions.add("spawn");
                                exampleRegions.add("pvp");
                                section.set("world", exampleRegions);
                                section.set("spawn", exampleRegions);
                                regionProtectAllow = section;
                            }
                            case "regionProtectOnlyBreakAllow"  -> {
                                ConfigurationSection section = new MemoryConfiguration();
                                ArrayList<String> exampleRegions = new ArrayList<>();
                                exampleRegions.add("spawn");
                                exampleRegions.add("pvp");
                                section.set("world", exampleRegions);
                                section.set("spawn", exampleRegions);
                                regionProtectOnlyBreakAllow = section;
                            }

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
                            case "databaseEnable" -> databaseEnable = false;
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

    public HashMap<String, List<String>> getRegionProtectAllowMap() {
        return regionsProtectAllowMap;
    }

    public void setRegionProtectAllowMap(HashMap<String, List<String>> value) {
        regionsProtectAllowMap = value;
        for(Map.Entry<String, List<String>> entry : value.entrySet()) {
            regionProtectAllow.set(entry.getKey(), entry.getValue());
        }
    }

    public HashMap<String, List<String>> getRegionProtectMap() {
        return regionsProtectMap;
    }

    public void setRegionProtectMap(HashMap<String, List<String>> value) {
        regionsProtectMap = value;
        for(Map.Entry<String, List<String>> entry : value.entrySet()) {
            regionProtect.set(entry.getKey(), entry.getValue());
        }
    }

    public HashMap<String, List<String>> getRegionProtectAllow() {
        return regionsProtectAllowMap;
    }

    public HashMap<String, List<String>> getRegionProtectOnlyBreakAllow() {
        return regionsOnlyBreakAllowMap;
    }

    public void setRegionProtectOnlyBreakAllow(HashMap<String, List<String>> value) {
        regionsOnlyBreakAllowMap = value;
        for (Map.Entry<String, List<String>> entry : value.entrySet()) {
            regionProtectAllow.set(entry.getKey(), entry.getValue());
        }
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
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.regionProtect", regionProtect);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.regionProtectAllow", regionProtectAllow);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.regionProtectOnlyBreakAllow", regionProtectOnlyBreakAllow);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.interactType", interactType);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.collisionWithVehicle", collisionWithVehicle);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.canSitAsPassengerInVehicle", canSitAsPassengerInVehicle);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.canDamageVehicle", canDamageVehicle);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.protectInteract.player.canTakeLecternBook", canTakeLecternBook);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWe", cmdWe);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeC", cmdWeC);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeP", cmdWeP);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeS", cmdWeS);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeU", cmdWeU);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.noProtectCmd.cmdWeCP", cmdWeCP);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.explodeEntity.enable", explodeEntity);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.regionMessageProtect", regionMessageProtect);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.regionMessageProtectWe", regionMessageProtectWe);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.console.enable", spyCommandNotifyConsole);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.admin.enable", spyCommandNotifyAdmin);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.sound.enable", spyCommandNotifyAdminPlaySoundEnable );
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.notify.sound.type", spyCommandNotifyAdminPlaySound);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.spySettings.spyCommandList", spyCommandList);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.databaseEnable", databaseEnable);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.host", mysqlsettings.getHost());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.port", mysqlsettings.getPort());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.name", mysqlsettings.getName());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.user", mysqlsettings.getUser());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.password", mysqlsettings.getPassword());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.table", mysqlsettings.getTable());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.maxPoolSize", mysqlsettings.getMaxPoolSize());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.maxLifetime", mysqlsettings.getMaxLifetime());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.connectionTimeout", mysqlsettings.getConnectionTimeout());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.dataSource.intervalReload", mysqlsettings.getIntervalReload());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wgRegionProtect.lang", lang);
            wgRegionProtect.getWgrpBukkitPlugin().saveConfig();
        } catch (Exception e) {
            wgRegionProtect.getWgrpBukkitPlugin().getLogger().severe("Could not save config.yml! Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public String getLang() {
        return lang;
    }

    public static class MySQLSettings {
        private final String host;
        private final int port;
        private final String name;
        private final String user;
        private final String password;
        private final String table;
        private final int maxPoolSize;
        private final int maxLifetime;
        private final int connectionTimeout;
        private final int intervalReload;

        MySQLSettings(String host, int port, String name, String user, String password, String table,
                      int maxPoolSize, int maxLifetime, int connectionTimeout, int intervalReload) {
            this.host = host;
            this.port = port;
            this.name = name;
            this.user = user;
            this.password = password;
            this.table = table;
            this.maxPoolSize = maxPoolSize;
            this.maxLifetime = maxLifetime;
            this.connectionTimeout = connectionTimeout;
            this.intervalReload = intervalReload;
        }

        public String getHost() {
            return host;
        }

        public int getPort() {
            return port;
        }

        public String getName() {
            return name;
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

        public int getIntervalReload() {
            return intervalReload;
        }
    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    private @interface CanRecover { }
}