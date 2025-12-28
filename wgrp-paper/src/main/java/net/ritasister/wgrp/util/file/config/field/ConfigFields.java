package net.ritasister.wgrp.util.file.config.field;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Enum representing configuration fields for the WorldGuardRegionProtect plugin.
 * Each enum constant corresponds to a specific configuration option, along with its
 * default value and path in the configuration file.
 */
public enum ConfigFields {

    CONFIG_VERSION("version", 5, "wgRegionProtect.version"),

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

    DENY_USE_SUPER_PICKAXE("denyUseSuperpickaxe", true,
            "wgRegionProtect.protectInteract.other.denyUseSuperpickaxe"
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

    CHECK_PLAYER_IN_STANDING_REGION("checkPlayerInStandingRegion", true,
            "wgRegionProtect.protectInteract.player.standingRegion.checkPlayerInStandingRegion"
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

    REGION_MESSAGE_PROTECT_BY_PLAYER("regionMessageProtectByPlayer", true,
            "wgRegionProtect.regionMessageProtectByPlayer"
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
    DATA_SOURCE_HOST("host", "localhost", "wgRegionProtect.dataSource.host"),
    DATA_SOURCE_PORT("port", 3306, "wgRegionProtect.dataSource.port"),
    DATA_SOURCE_DATABASE("database", "database", "wgRegionProtect.dataSource.database"),
    DATA_SOURCE_USER("root", "root", "wgRegionProtect.dataSource.root"),
    DATA_SOURCE_PASSWORD("password", "password", "wgRegionProtect.dataSource.password"),
    DATA_SOURCE_TABLE("wgrp_logs", "wgrp_logs", "wgRegionProtect.dataSource.wgrp_logs"),
    DATA_SOURCE_MAX_POOL_SIZE("maxPoolSize", 10, "wgRegionProtect.dataSource.maxPoolSize"),
    DATA_SOURCE_MAX_LIFE_TIME("maxLifetime", 1800, "wgRegionProtect.dataSource.maxLifetime"),
    DATA_SOURCE_CONNECTION_TIMEOUT("connectionTimeout", 5000, "wgRegionProtect.dataSource.connectionTimeout"),
    DATA_SOURCE_USE_SSL("useSsl", true, "wgRegionProtect.dataSource.useSsl"),
    DATA_SOURCE_INTERVAL_RELOAD("intervalReload", 60, "wgRegionProtect.dataSource.intervalReload");

    private final String field;
    private final Object defaultValue;
    private final String path;
    private FieldType fieldType;

    private static final Map<String, ConfigFields> CONFIG_FIELDS = new HashMap<>();
    private static final Map<Class<?>, FieldType> SIMPLE_TYPE_MAP = Map.of(
            String.class, FieldType.STRING,
            Boolean.class, FieldType.BOOLEAN,
            Integer.class, FieldType.INTEGER,
            Double.class, FieldType.DOUBLE,
            Long.class, FieldType.LONG,
            Float.class, FieldType.FLOAT
    );

    static {
        for (ConfigFields fields : values()) {
            if (fields.field != null) {
                CONFIG_FIELDS.put(fields.name().toLowerCase(Locale.ROOT), fields);
            }

            if (fields.defaultValue != null) {
                fields.fieldType = resolveFieldType(fields.defaultValue);
            } else {
                fields.fieldType = FieldType.STRING;
            }
        }
    }

    ConfigFields(String field, Object defaultValue, String path) {
        this.field = field;
        this.defaultValue = defaultValue;
        this.path = path;
    }

    /**
     * Retrieves the ConfigFields enum constant corresponding to the given field name.
     *
     * @param field The name of the configuration field.
     * @return The corresponding ConfigFields enum constant, or null if not found.
     */
    @ApiStatus.Internal
    @Contract("null -> null")
    @Nullable
    public static ConfigFields getField(String field) {
        if (field == null) {
            return null;
        }
        return CONFIG_FIELDS.get(field.toLowerCase(Locale.ROOT));
    }

    /**
     * Retrieves the default value of this configuration field.
     *
     * @return The default value of the configuration field.
     */
    public @NotNull String getPath() {
        return path;
    }

    /**
     * Retrieves the default value of this configuration field.
     *
     * @return The default value of the configuration field.
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    private static FieldType resolveFieldType(@NonNull Object value) {
        final FieldType simpleType = SIMPLE_TYPE_MAP.get(value.getClass());
        if (simpleType != null) return simpleType;

        if (value instanceof List<?> list) {
            if (!list.isEmpty()) {
                final Object first = list.get(0);
                final FieldType firstType = SIMPLE_TYPE_MAP.get(first.getClass());

                return switch (firstType) {
                    case INTEGER -> FieldType.INTEGER_LIST;
                    case DOUBLE -> FieldType.DOUBLE_LIST;
                    case LONG -> FieldType.LONG_LIST;
                    case FLOAT -> FieldType.FLOAT_LIST;
                    case BOOLEAN -> FieldType.BOOLEAN_LIST;
                    default -> FieldType.STRING_LIST;
                };
            }
            return FieldType.STRING_LIST;
        }

        throw new IllegalArgumentException("Unsupported default value type: " + value.getClass());
    }

    private void ensure(FieldType expected) {
        if (this.fieldType != expected) {
            throw new IllegalStateException(name() + " is " + fieldType + ", not " + expected);
        }
    }

    /**
     * Retrieves the value of this configuration field as a String.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a String.
     * @throws IllegalStateException if the field type is not STRING.
     */
    public String asString(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.STRING);
        return plugin.getBootstrap().getLoader().getConfig().getString(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a List of Strings.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a List of Strings.
     * @throws IllegalStateException if the field type is not STRING_LIST.
     */
    public @NonNull List<String> asStringList(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.STRING_LIST);
        return plugin.getBootstrap().getLoader().getConfig().getStringList(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a List of Integers.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a List of Integers.
     * @throws IllegalStateException if the field type is not INTEGER_LIST.
     */
    public @NotNull List<Integer> asIntegerList(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.INTEGER_LIST);
        return plugin.getBootstrap().getLoader().getConfig().getIntegerList(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a List of Doubles.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a List of Doubles.
     * @throws IllegalStateException if the field type is not DOUBLE_LIST.
     */
    public @NotNull List<Double> asDoubleList(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.DOUBLE_LIST);
        return plugin.getBootstrap().getLoader().getConfig().getDoubleList(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a List of Longs.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a List of Longs.
     * @throws IllegalStateException if the field type is not LONG_LIST.
     */
    public @NonNull List<Long> asLongList(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.LONG_LIST);
        return plugin.getBootstrap().getLoader().getConfig().getLongList(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a List of Floats.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a List of Floats.
     * @throws IllegalStateException if the field type is not FLOAT_LIST.
     */
    public @NonNull List<Float> asFloatList(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.FLOAT_LIST);
        return plugin.getBootstrap().getLoader().getConfig().getFloatList(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a List of Booleans.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a List of Booleans.
     * @throws IllegalStateException if the field type is not BOOLEAN_LIST.
     */
    public @NonNull List<Boolean> asBooleanList(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.BOOLEAN_LIST);
        return plugin.getBootstrap().getLoader().getConfig().getBooleanList(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a boolean.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a boolean.
     * @throws IllegalStateException if the field type is not BOOLEAN.
     */
    public boolean asBoolean(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.BOOLEAN);
        return plugin.getBootstrap().getLoader().getConfig().getBoolean(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a double.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a double.
     * @throws IllegalStateException if the field type is not DOUBLE.
     */
    public double asDouble(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.DOUBLE);
        return plugin.getBootstrap().getLoader().getConfig().getDouble(getPath());
    }

    /**
     * Retrieves the value of this configuration field as an integer.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as an integer.
     * @throws IllegalStateException if the field type is not INTEGER.
     */
    public int asInt(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.INTEGER);
        return plugin.getBootstrap().getLoader().getConfig().getInt(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a long.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a long.
     * @throws IllegalStateException if the field type is not LONG.
     */
    public long asLong(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.LONG);
        return plugin.getBootstrap().getLoader().getConfig().getLong(getPath());
    }

    /**
     * Retrieves the value of this configuration field as a float.
     *
     * @param plugin The instance of WorldGuardRegionProtectPaperBase to access the configuration.
     * @return The value of the configuration field as a float.
     * @throws IllegalStateException if the field type is not FLOAT.
     */
    public float asFloat(@NonNull WorldGuardRegionProtectPaperPlugin plugin) {
        ensure(FieldType.FLOAT);
        return (float) plugin.getBootstrap().getLoader().getConfig().getDouble(getPath());
    }
}
