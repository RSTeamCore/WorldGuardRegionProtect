package net.ritasister.wgrp.util.file.config;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperBase;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public enum ConfigFields {

    CONFIG_VERSION("configVersion", 2, "wgRegionProtect.version"),

    LANG("lang", "en_US", "wgRegionProtect.lang"),

    SEND_NO_UPDATE("sendNoUpdate", true, "wgRegionProtect.updateChecker.enable"),

    UPDATE_CHECKER("updateChecker", true, "wgRegionProtect.updateChecker.sendNoUpdate"),

    INTERACT_TYPE("interactType", List.of(
            "armor_stand", "end_crystal", "bucket",
            "water_bucket", "lava_bucket", "tropical_fish_bucket",
            "pufferfish_bucket", "axolotl_bucket", "cod_bucket",
            "salmon_bucket", "tadpole_bucket"
    ),
            "wgRegionProtect.protectInteract.interactType"
    ),

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

    ANIMAL_TYPE("animalType", List.of(
            "tropical_fish", "axolotl", "turtle",
            "sniffer", "camel"
    ),
            "wgRegionProtect.protectInteract.animalType"
    ),

    MONSTER_TYPE("monsterType", List.of(
            "tropical_fish", "axolotl", "turtle",
            "sniffer", "camel"
    ),
            "wgRegionProtect.protectInteract.monsterType"
    ),

    ENEMY_ENTITY_TYPE("enemyType", List.of(
            "ender_dragon", "magma_cube", "slime",
            "ghast"
    ),
            "wgRegionProtect.protectInteract.enemyType"
    ),

    MISC_ENTITY_TYPE("miscEntityType", List.of(
            "egg", "ender_pearl", "experience_orb", "snowball"
    ),
            "wgRegionProtect.protectInteract.miscEntityType"
    ),

    WATER_MOB_TYPE("waterMobType", List.of(
            "tropical_fish", "axolotl", "turtle",
            "sniffer", "camel"
    ),
            "wgRegionProtect.protectInteract.waterMobType"
    ),

    SIGN_TYPE("signType", List.of(
            "primed_tnt", "end_crystal", "minecart_tnt",
            "creeper", "wither_skull"
    ),
            "wgRegionProtect.protectInteract.signType"
    ),

    ENTITY_EXPLODE_TYPE("entityExplodeType", List.of(
            "primed_tnt", "end_crystal", "minecart_tnt",
            "creeper", "wither_skull"
    ),
            "wgRegionProtect.protectInteract.entityExplodeType"
    ),

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

    DENY_COLLISION_WITH_VEHICLE("denyCollisionWithVehicle", true,
            "wgRegionProtect.protectInteract.player.vehicle.denyCollisionWithVehicle"
    ),

    DENY_SIT_AS_PASSENGER_IN_VEHICLE("denySitAsPassengerInVehicle", true,
            "wgRegionProtect.protectInteract.player.vehicle.denySitAsPassengerInVehicle"
    ),

    DENY_DAMAGE_VEHICLE("denyDamageVehicle", true,
            "wgRegionProtect.protectInteract.player.vehicle.denyDamageVehicle"
    ),

    DENY_TAKE_LECTERN_BOOK("denyTakeLecternBook", true,
            "wgRegionProtect.protectInteract.player.tools.denyTakeLecternBook"
    ),

    DENY_MANIPULATE_WITH_FLOWERPOT("denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot", true,
            "wgRegionProtect.protectInteract.player.misc.denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot"
    ),

    DENY_LAVA_FLOW_TO_REGION("denyLavaFlowToRegion", true,
            "wgRegionProtect.protectInteract.other.denyLavaFlowToRegion"
    ),

    DENY_FORM_BLOCK_FROM_LAVA_AND_WATER("denyFormObsidianOrCobbleStone", true,
            "wgRegionProtect.protectInteract.other.denyFormObsidianOrCobbleStone"
    ),

    DENY_CREATURE_SPAWN("denyCreatureSpawn", true,
            "wgRegionProtect.protectInteract.mobs.denyCreatureSpawn"
    ),

    DENY_MOB_SPAWN_FROM_SPAWNER("denyMobSpawner", true,
            "wgRegionProtect.protectInteract.mobs.denyMobSpawner"
    ),

    DENY_MOB_NATURALLY_SPAWN("denyMobSpawn", true,
            "wgRegionProtect.protectInteract.mobs.denyMobSpawn"
    ),

    DENY_WATER_FLOW_TO_REGION("denyWaterFlowToRegion", true,
            "wgRegionProtect.protectInteract.other.denyWaterFlowToRegion"
    ),

    DENY_INTERACT_WITH_FLOWER_POT("denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot", true,
            "wgRegionProtect.protectInteract.player.misc.denyTakeOrPlaceNaturalBlockOrItemIOFlowerPot"
    ),

    DENY_PLACE_ITEM_FRAME_OR_PAINTING("denyPlaceItemFrameOrPainting", true,
            "wgRegionProtect.protectInteract.player.itemFrame.denyPlaceItemFrameOrPainting"
    ),

    DENY_INTERACT_WITH_ITEM_FRAME("denyInteractWithItemFrame", true,
            "wgRegionProtect.protectInteract.player.itemFrame.denyInteractWithItemFrame"
    ),

    DENY_DAMAGE_ITEM_FRAME_OR_PAINTING("denyDamageItemFrameOrPainting", true,
            "wgRegionProtect.protectInteract.player.itemFrame.denyDamageItemFrameOrPainting"
    ),

    DENY_STONECUTTER_RECIPE_SELECT("denyStonecutterRecipeSelect", true,
            "wgRegionProtect.protectInteract.player.tools.denyStonecutterRecipeSelect"
    ),

    DENY_LOOM_PATTERN_SELECT("denyLoomPatternSelect", true,
            "wgRegionProtect.protectInteract.player.tools.denyLoomPatternSelect"
    ),


    CMD_WE("cmdWe", List.of(
            "//set", "//replace", "//overlay",
            "//walls", "//deform", "//fill",
            "//fillr", "//fixlava", "//hollow",
            "//move", "//stack", "//smooth",
            "//cut", "//replacenear"
    ),
            "wgRegionProtect.noProtectCmd.cmdWe"
    ),

    CMD_WE_C("cmdWeC", List.of("//cyl", "//hcyl", "//drain", "//rep"),
            "wgRegionProtect.noProtectCmd.cmdWeC"
    ),

    CMD_WE_P("cmdWeP", List.of("//pyramid", "//hpyramid"),
            "wgRegionProtect.noProtectCmd.cmdWeP"
    ),

    CMD_WE_S("cmdWeS", List.of("//sphere", "//hsphere"),
            "wgRegionProtect.noProtectCmd.cmdWeS"
    ),

    CMD_WE_U("cmdWeU", List.of("//up", "/up"),
            "wgRegionProtect.noProtectCmd.cmdWeU"
    ),

    CMD_WE_CP("cmdWeCP", List.of("//paste", "//place", "//replacenear", "//hollow"),
            "wgRegionProtect.noProtectCmd.cmdWeCP"
    ),


    DENY_EXPLODE_ENTITY("explodeEntity", true,
            "wgRegionProtect.explodeEntity.enable"
    ),


    REGION_MESSAGE_PROTECT("regionMessageProtect", true,
            "wgRegionProtect.regionMessageProtect"
    ),

    REGION_MESSAGE_PROTECT_WE("regionMessageProtectWe", true,
            "wgRegionProtect.regionMessageProtectWe"
    ),

    IS_SPY_COMMAND_NOTIFY_CONSOLE_ENABLE("isSpyCommandNotifyConsoleEnable", true,
            "wgRegionProtect.spySettings.notify.console.enable"
    ),

    IS_SPY_COMMAND_NOTIFY_ADMIN_ENABLE("isSpyCommandNotifyAdminEnable", true,
            "wgRegionProtect.spySettings.notify.admin.enable"
    ),

    IS_SPY_COMMAND_NOTIFY_ADMIN_PLAY_SOUND_ENABLE("spyCommandNotifyAdminPlaySoundEnable", true,
            "wgRegionProtect.spySettings.notify.sound.enable"
    ),

    SPY_COMMAND_NOTIFY_PLAY_SOUND_TYPE("spyCommandNotifyAdminPlaySound", "BLOCK_ANVIL_PLACE",
            "wgRegionProtect.spySettings.notify.sound.type"
    ),

    SPY_COMMAND_LIST("spyCommandList", List.of(
            "//set", "//replace", "//overlay",
            "//walls", "//deform", "//fill",
            "//fillr", "//fixlava", "//hollow",
            "//move", "//stack", "//smooth",
            "//cut", "//replacenear"
    ), "wgRegionProtect.spySettings.spyCommandList"),

    DATA_SOURCE_ENABLE("enable", false, "wgRegionProtect.dataSource.enable"),

    DATA_SOURCE_HOST("localhost", "localhost", "wgRegionProtect.dataSource.localhost"),
    DATA_SOURCE_PORT("port", 3306, "wgRegionProtect.dataSource.port"),
    DATA_SOURCE_DATABASE("database", "database", "wgRegionProtect.dataSource.database"),
    DATA_SOURCE_USER("root", "root", "wgRegionProtect.dataSource.root"),
    DATA_SOURCE_PASSWORD("password", "password", "wgRegionProtect.dataSource.password"),
    DATA_SOURCE_TABLE("wgrp_logs", "wgrp_logs", "wgRegionProtect.dataSource.wgrp_logs"),
    DATA_SOURCE_MAX_POOL_SIZE("maxPoolSize", 10, "wgRegionProtect.dataSource.maxPoolSize"),
    DATA_SOURCE_MAX_LIFE_TIME("maxLifetime", 1800, "wgRegionProtect.dataSource.maxLifetime"),
    DATA_SOURCE_CONNECTION_TIMEOUT("connectionTimeout", 5000, "wgRegionProtect.dataSource.connectionTimeout"),
    DATA_SOURCE_USE_SSL("useSsl", true, "wgRegionProtect.dataSource.useSsl"),
    DATA_SOURCE_INTERVAL_RELOAD("intervalReload", 60, "wgRegionProtect.dataSource.intervalReload"

    );

    private final String field;
    private Object param;
    private List<String> elements = new ArrayList<>();
    private boolean bool;
    private int integer;
    private double doubleValue;
    private final String path;
    private static final Map<String, ConfigFields> CONFIG_FIELDS = new HashMap<>();
    private final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

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

    ConfigFields(String field, boolean bool, String path) {
        this.field = field;
        this.bool = bool;
        this.path = path;
    }

    ConfigFields(String field, double doubleValue, String path) {
        this.field = field;
        this.doubleValue = doubleValue;
        this.path = path;
    }

    ConfigFields(String field, int integer, String path) {
        this.field = field;
        this.integer = integer;
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

    public @NotNull String getPath() {
        return path;
    }

    public Object get(@NotNull Plugin wgrpBase) {
        if (!elements.isEmpty()) {
            return elements = wgrpBase.getConfig().getStringList(getPath());
        }
        if (param != null) {
            if (isBooleanCheck(param.toString())) {
                return bool = wgrpBase.getConfig().getBoolean(getPath());
            }
            if (isNumeric(param.toString())) {
                return integer = wgrpBase.getConfig().getInt(getPath());
            }
            if (isDouble(param.toString())) {
                return doubleValue = wgrpBase.getConfig().getDouble(getPath());
            }
        }
        return param = wgrpBase.getConfig().getString(getPath());
    }

    public List<String> getList(@NotNull WorldGuardRegionProtectPaperBase wgrpBase) {
        return elements = wgrpBase.getConfig().getStringList(getPath());
    }

    public boolean getBoolean(@NotNull WorldGuardRegionProtectPaperBase wgrpBase) {
        return bool = wgrpBase.getConfig().getBoolean(getPath());
    }

    private boolean isBooleanCheck(@NotNull String string) {
        return string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false");
    }

    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    private boolean isDouble(String string) {
        try {
            Double.valueOf(string);
        } catch (Exception ex) { // Not a valid double value
            return false;
        }
        return true;
    }
}
