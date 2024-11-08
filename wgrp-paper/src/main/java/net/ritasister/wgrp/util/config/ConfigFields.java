package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.api.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.annotation.CanRecover;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public enum ConfigFields {

    @CanRecover
    LANG("lang", "en_US", "wgRegionProtect.lang"),
    @CanRecover
    SEND_NO_UPDATE("sendNoUpdate", true, "wgRegionProtect.updateChecker.enable"),
    @CanRecover
    UPDATE_CHECKER("updateChecker", true, "wgRegionProtect.updateChecker.sendNoUpdate"),
    @CanRecover
    INTERACT_TYPE("interactType", List.of(
            "armor_stand", "end_crystal", "bucket",
            "water_bucket", "lava_bucket", "tropical_fish_bucket",
            "pufferfish_bucket", "axolotl_bucket", "cod_bucket",
            "salmon_bucket", "tadpole_bucket"),
            "wgRegionProtect.protectInteract.interactType"),
    @CanRecover
    VEHICLE_TYPE("vehicleType", List.of(
            "minecart", "tnt_minecart", "command_block_minecart",
            "hopper_minecart", "chest_minecart", "furnace_minecart",
            "oak_boat", "oak_chest_boat",
            "spruce_boat", "spruce_chest_boat",
            "birch_boat", "birch_chest_boat",
            "jungle_boat", "jungle_chest_boat",
            "acacia_boat", "acacia_chest_boat",
            "dark_oak_boat", "dark_oak_chest_boat",
            "mangrove_boat", "mangrove_chest_boat",
            "cherry_boat", "cherry_chest_boat",
            "bamboo_raft", "bamboo_chest_raft"
    ),
            "wgRegionProtect.protectInteract.vehicleType"
    ),
    @CanRecover
    ANIMAL_TYPE("animalType", List.of(
            "tropical_fish", "axolotl", "turtle",
            "sniffer", "camel"
    ),
            "wgRegionProtect.protectInteract.animalType"
    ),
    @CanRecover
    MONSTER_TYPE("monsterType", List.of(
            "tropical_fish", "axolotl", "turtle",
            "sniffer", "camel"
    ),
            "wgRegionProtect.protectInteract.monsterType"
    ),
    @CanRecover
    WATER_MOB_TYPE("waterMobType", List.of(
            "tropical_fish", "axolotl", "turtle",
            "sniffer", "camel"
    ),
            "wgRegionProtect.protectInteract.waterMobType"
    ),
    @CanRecover
    SIGN_TYPE("signType", List.of(
            "primed_tnt", "end_crystal", "minecart_tnt",
            "creeper", "wither_skull"
    ),
            "wgRegionProtect.protectInteract.signType"
    ),
    @CanRecover
    ENTITY_EXPLODE_TYPE("entityExplodeType", List.of(
            "primed_tnt", "end_crystal", "minecart_tnt",
            "creeper", "wither_skull"
    ),
            "wgRegionProtect.protectInteract.entityExplodeType"
    ),
    @CanRecover
    NATURAL_BLOCK_OR_ITEM("naturalBlockOrItem", List.of(
            "oak_sapling", "spruce_sapling", "birch_sapling",
            "jungle_sapling", "acacia_sapling", "dark_oak_sapling",
            "mangrove_propagule", "dead_bush", "fern",
            "azalea", "flowering_azalea",
            "dandelion", "poppy", "blue_orchid", "allium", "azure_bluet",
            "red_tulip", "orange_tulip", "white_tulip", "pink_tulip",
            "oxeye_daisy", "cornflower", "lily_of_the_valley",
            "bamboo", "sugar_cane", "cactus", "wither_rose",
            "crimson_roots", "warped_roots", "cherry_sapling", "torchflower"
    ),
            "wgRegionProtect.protectInteract.naturalBlockOrItem"
    ),
    @CanRecover
    DENY_COLLISION_WITH_VEHICLE("denyCollisionWithVehicle", true,
            "wgRegionProtect.protectInteract.player.vehicle.denyCollisionWithVehicle"
    ),
    @CanRecover
    DENY_SIT_AS_PASSENGER_IN_VEHICLE("denySitAsPassengerInVehicle", true,
            "wgRegionProtect.protectInteract.player.vehicle.denySitAsPassengerInVehicle"
    ),
    @CanRecover
    DENY_DAMAGE_VEHICLE("denyDamageVehicle", true,
            "wgRegionProtect.protectInteract.player.vehicle.denyDamageVehicle"
    ),
    @CanRecover
    DENY_TAKE_LECTERN_BOOK("denyTakeLecternBook", true,
            "wgRegionProtect.protectInteract.player.tools.denyTakeLecternBook"
    ),
    @CanRecover
    DENY_MANIPULATE_WITH_FLOWERPOT("denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot", true,
            "wgRegionProtect.protectInteract.player.misc.denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot"
    ),
    @CanRecover
    DENY_PLACE_ITEM_FRAME_OR_PAINTING("denyPlaceItemFrameOrPainting", true,
            "wgRegionProtect.protectInteract.player.itemFrame.denyPlaceItemFrameOrPainting"
    ),
    @CanRecover
    DENY_INTERACT_WITH_ITEM_FRAME("denyInteractWithItemFrame", true,
            "wgRegionProtect.protectInteract.player.itemFrame.denyInteractWithItemFrame"
    ),
    @CanRecover
    DENY_DAMAGE_ITEM_FRAME_OR_PAINTING("denyDamageItemFrameOrPainting", true,
            "wgRegionProtect.protectInteract.player.itemFrame.denyDamageItemFrameOrPainting"
    ),
    @CanRecover
    DENY_STONECUTTER_RECIPE_SELECT("denyStonecutterRecipeSelect", true,
            "wwgRegionProtect.protectInteract.player.tools.denyStonecutterRecipeSelect"
    ),
    @CanRecover
    DENY_LOOM_PATTERN_SELECT("denyLoomPatternSelect", true,
            "wgRegionProtect.protectInteract.player.tools.denyLoomPatternSelect"
    ),

    @CanRecover
    CMD_WE("cmdWe", List.of(
            "//set", "//replace", "//overlay",
            "//walls", "//deform", "//fill",
            "//fillr", "//fixlava", "//hollow",
            "//move", "//stack", "//smooth",
            "//cut", "//replacenear"
    ),
            "wgRegionProtect.noProtectCmd.cmdWe"
    ),
    @CanRecover
    CMD_WE_C("cmdWeC", List.of("//cyl", "//hcyl", "//drain", "//rep"),
            "wgRegionProtect.noProtectCmd.cmdWeC"
    ),
    @CanRecover
    CMD_WE_P("cmdWeP", List.of("//pyramid", "//hpyramid"),
            "wgRegionProtect.noProtectCmd.cmdWeP"
    ),
    @CanRecover
    CMD_WE_S("cmdWeS", List.of("//sphere", "//hsphere"),
            "wgRegionProtect.noProtectCmd.cmdWeS"
    ),
    @CanRecover
    CMD_WE_U("cmdWeU", List.of("//up", "/up"),
            "wgRegionProtect.noProtectCmd.cmdWeU"
    ),
    @CanRecover
    CMD_WE_CP("cmdWeCP", List.of("//paste", "//place", "//replacenear", "//hollow"),
            "wgRegionProtect.noProtectCmd.cmdWeCP"
    ),

    @CanRecover
    DENY_EXPLODE_ENTITY("explodeEntity", true,
            "wgRegionProtect.explodeEntity.enable"
    ),

    @CanRecover
    REGION_MESSAGE_PROTECT("regionMessageProtect", true,
            "wgRegionProtect.regionMessageProtect"
    ),
    @CanRecover
    REGION_MESSAGE_PROTECT_WE("regionMessageProtectWe", true,
            "wgRegionProtect.regionMessageProtectWe"
    ),
    @CanRecover
    IS_SPY_COMMAND_NOTIFY_CONSOLE_ENABLE("isSpyCommandNotifyConsoleEnable", true,
            "wgRegionProtect.spySettings.notify.console.enable"
    ),
    @CanRecover
    IS_SPY_COMMAND_NOTIFY_ADMIN_ENABLE("isSpyCommandNotifyAdminEnable", true,
            "wgRegionProtect.spySettings.notify.admin.enable"
    ),
    @CanRecover
    IS_SPY_COMMAND_NOTIFY_ADMIN_PLAY_SOUND_ENABLE("spyCommandNotifyAdminPlaySoundEnable", true,
            "wgRegionProtect.spySettings.notify.sound.enable"
    ),
    @CanRecover
    SPY_COMMAND_NOTIFY_PLAY_SOUND_TYPE("spyCommandNotifyAdminPlaySound", "BLOCK_ANVIL_PLACE",
            "wgRegionProtect.spySettings.notify.sound.type"),
    @CanRecover
    SPY_COMMAND_LIST("spyCommandList", List.of(
            "//set", "//replace", "//overlay",
            "//walls", "//deform", "//fill",
            "//fillr", "//fixlava", "//hollow",
            "//move", "//stack", "//smooth",
            "//cut", "//replacenear"
    ), "wgRegionProtect.spySettings.spyCommandList"),
    ;

    /*@CanRecover
    DATABASE_ENABLE("databaseEnable", true, "wgRegionProtect.dataSource.enable"),
    @CanRecover
    MYSQL_SETTINGS("mysqlSettings", String.valueOf(new MySQLSettings(
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
            60
    )), "wgRegionProtect.protectInteract.interactType");*/

    private final String field;
    private String param;
    private List<String> elements = new ArrayList<>();
    private boolean value;
    private final String path;
    private MySQLSettings mysqlsettings;
    private static final Map<String, ConfigFields> CONFIG_FIELDS = new HashMap<>();

    static {
        for (ConfigFields fields : values()) {
            if (fields.field != null) {
                CONFIG_FIELDS.put(fields.name().toLowerCase(Locale.ROOT), fields);
            }
        }
    }

    ConfigFields(String field, List<String> elements, String path) {
        this.field = field;
        this.elements = elements;
        this.path = path;
    }

    ConfigFields(String field, boolean value, String path) {
        this.field = field;
        this.value = value;
        this.path = path;
    }

    ConfigFields(String field, String param, String path) {
        this.field = field;
        this.param = param;
        this.path = path;
    }

    @ApiStatus.Internal
    @Contract("null -> null")
    @Nullable
    public static ConfigFields getField(String field) {
        if (field == null) {
            return null;
        }
        return CONFIG_FIELDS.get(field.toLowerCase(Locale.ROOT));
    }

    @Nullable
    public Object getParam() {
        if(param != null && elements.contains(param)) {
            return elements;
        }
        if(value) {
            return value;
        }
        return param;
    }

    //TODO
    public @Unmodifiable Object getVariable() {
        return Bukkit.spigot().getConfig().get(param);
    }

    public String getPath() {
        if(path != null) {
            return String.format("Path %s cannot be null!", path);
        }
        return path;
    }

    public MySQLSettings getMysqlsettings() {
        return mysqlsettings;
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

        MySQLSettings(
                String host,
                int port,
                String database,
                String user,
                String password,
                String table,
                int maxPoolSize,
                int maxLifetime,
                int connectionTimeout,
                boolean useSsl,
                int intervalReload
        ) {
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
}
