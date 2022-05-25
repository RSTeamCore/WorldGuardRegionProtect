package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private final WorldGuardRegionProtect wgRegionProtect;
    private FileConfiguration config;


    private String lang;
    private List<String> regionProtect;
    private List<String> regionProtectAllow;
    private List<String> regionProtectOnlyBreakAllow;
    private List<String> interactType;
    private List<String> spyCommandList;
    private List<String> cmdWe;
    private List<String> cmdWeC;
    private List<String> cmdWeP;
    private List<String> cmdWeS;
    private List<String> cmdWeU;
    private List<String> cmdWeCP;
    private boolean regionMessageProtect;
    private boolean regionMessageProtectWe;
    private boolean spyCommandNotifyConsole;
    private boolean spyCommandNotifyAdmin;
    private boolean explodeEntity;
    private String spyCommandNotifyAdminPlaySound;
    private boolean spyCommandNotifyAdminPlaySoundEnable;
    private boolean databaseEnable;

    private MySQLSettings mysqlsettings;

    public Config(WorldGuardRegionProtect wgRegionProtect, FileConfiguration config) {
        this.wgRegionProtect = wgRegionProtect;
        this.config = config;
        reload();
    }

    @SuppressWarnings("unchecked")
    public void reload() {
        wgRegionProtect.getWgrpBukkitPlugin().saveDefaultConfig();
        wgRegionProtect.getWgrpBukkitPlugin().reloadConfig();
        config = wgRegionProtect.getWgrpBukkitPlugin().getConfig();

        try {
            lang = config.getString("wg_region_protect.lang");
            regionProtect = (List<String>)config.getList("wg_region_protect.region_protect");
            regionProtectAllow = (List<String>)config.getList("wg_region_protect.region_protect_allow");
            regionProtectOnlyBreakAllow = (List<String>)config.getList("wg_region_protect.region_protect_only_break_allow");
            interactType = (List<String>)config.getList("wg_region_protect.interact_type");

            cmdWe = (List<String>)config.getList("wg_region_protect.no_protect_cmd.command_we");
            cmdWeC = (List<String>)config.getList("wg_region_protect.no_protect_cmd.command_c");
            cmdWeP = (List<String>)config.getList("wg_region_protect.no_protect_cmd.command_p");
            cmdWeS = (List<String>)config.getList("wg_region_protect.no_protect_cmd.command_s");
            cmdWeU = (List<String>)config.getList("wg_region_protect.no_protect_cmd.command_u");
            cmdWeCP = (List<String>)config.getList("wg_region_protect.no_protect_cmd.command_cp");

            explodeEntity = config.getBoolean("wg_region_protect.explodeEntity.enable");

            regionMessageProtect = config.getBoolean("wg_region_protect.protect_message");
            regionMessageProtectWe = config.getBoolean("wg_region_protect.protect_we_message");
            spyCommandNotifyConsole = config.getBoolean("wg_region_protect.spy_command.notify.console.enable");
            spyCommandNotifyAdmin = config.getBoolean("wg_region_protect.spy_command.notify.admin.enable");
            spyCommandNotifyAdminPlaySoundEnable = config.getBoolean("wg_region_protect.spy_command.notify.sound.enable");
            spyCommandNotifyAdminPlaySound = config.getString("wg_region_protect.spy_command.notify.sound.type");
            spyCommandList = (List<String>)config.getList("wg_region_protect.spy_command.spy_command_list");

            //Database settings.
            databaseEnable = config.getBoolean("wg_region_protect.dataSource.databaseEnable");
            mysqlsettings = new MySQLSettings(
                    config.getString("wg_region_protect.dataSource.host"),
                    config.getInt("wg_region_protect.dataSource.port"),
                    config.getString("wg_region_protect.dataSource.name"),
                    config.getString("wg_region_protect.dataSource.user"),
                    config.getString("wg_region_protect.dataSource.password"),
                    config.getString("wg_region_protect.dataSource.table"),
                    config.getInt("wg_region_protect.dataSource.maxPoolSize"),
                    config.getInt("wg_region_protect.dataSource.maxLifetime"),
                    config.getInt("wg_region_protect.dataSource.connectionTimeout"),
                    config.getInt("wg_region_protect.dataSource.intervalReload")
            );
        } catch (Exception e) {
            wgRegionProtect.getWgrpBukkitPlugin().getLogger().severe("Could not load config.yml! Error: " + e.getLocalizedMessage());
            e.printStackTrace();
        }


        for(Field f : this.getClass().getFields()) {
            try {
                if(f.get(this.getClass()).equals(null)) {
                    switch (f.getName()) {
                        case "regionProtect" -> regionProtect = List.of("spawn", "pvp");
                        case "regionProtectAllow" -> regionProtectAllow = new ArrayList<>();
                        case "regionProtectOnlyBreakAllow" -> regionProtectOnlyBreakAllow = new ArrayList<>();
                        case "interactType" -> interactType = List.of(
                                "armor_stand", "end_crystal", "minecart",
                                "explosive_minecart", "command_minecart", "hopper_minecart",
                                "storage_minecart", "powered_minecart", "boat",
                                "boat_spruce", "boat_birch", "boat_jungle",
                                "boat_acacia", "boat_dark_oak", "bucket",
                                "water_bucket", "lava_bucket");

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
        saveConfig();
    }

    public List<String> getRegionProtect() {
        return regionProtect;
    }

    public List<String> getRegionProtectAllow() {
        return regionProtectAllow;
    }

    public List<String> getRegionProtectOnlyBreakAllow() {
        return regionProtectOnlyBreakAllow;
    }

    public List<String> getInteractType() {
        return interactType;
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
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.region_protect", regionProtect);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.region_protect_allow", regionProtectAllow);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.region_protect_only_break_allow", regionProtectOnlyBreakAllow);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.interact_type", interactType);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.no_protect_cmd.command_we", cmdWe);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.no_protect_cmd.command_c", cmdWeC);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.no_protect_cmd.command_p", cmdWeP);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.no_protect_cmd.command_s", cmdWeS);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.no_protect_cmd.command_u", cmdWeU);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.no_protect_cmd.command_cp", cmdWeCP);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.explodeEntity.enable", explodeEntity);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.protect_message", regionMessageProtect);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.protect_we_message", regionMessageProtectWe);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.spy_command.notify.console.enable", spyCommandNotifyConsole);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.spy_command.notify.admin.enable", spyCommandNotifyAdmin);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.spy_command.notify.sound.enable", spyCommandNotifyAdminPlaySoundEnable );
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.spy_command.notify.sound.type", spyCommandNotifyAdminPlaySound);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.spy_command.spy_command_list", spyCommandList);

            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.databaseEnable", databaseEnable);
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.host", mysqlsettings.getHost());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.port", mysqlsettings.getPort());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.name", mysqlsettings.getName());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.user", mysqlsettings.getUser());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.password", mysqlsettings.getPassword());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.table", mysqlsettings.getTable());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.maxPoolSize", mysqlsettings.getMaxPoolSize());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.maxLifetime", mysqlsettings.getMaxLifetime());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.connectionTimeout", mysqlsettings.getConnectionTimeout());
            wgRegionProtect.getWgrpBukkitPlugin().getConfig().set("wg_region_protect.dataSource.intervalReload", mysqlsettings.getIntervalReload());
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
}