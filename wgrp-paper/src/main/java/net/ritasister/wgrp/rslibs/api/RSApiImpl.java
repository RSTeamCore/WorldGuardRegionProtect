package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.config.ParamsVersionCheck;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.permissions.PermissionsCheck;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckTypeProvider;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.ConfigType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.file.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class RSApiImpl implements MessagingService<Player>, PermissionsCheck<Player, Entity> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private final EntityCheckTypeProvider entityCheckTypeProvider;
    private final Messages messages;
    private final ParamsVersionCheck<ConfigType, YamlConfiguration> paramsVersionCheck;

    public final static String SUPPORTED_VERSION_RANGE = "1.20 - 1.21.4";
    public final static List<String> SUPPORTED_VERSION = Arrays.asList(
            "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6",
            "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4"
    );

    public RSApiImpl(
            final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin,
            final ParamsVersionCheck<ConfigType, YamlConfiguration> check
    ) {
        this.wgrpPlugin = wgrpPlugin;
        this.messages = wgrpPlugin.getConfigLoader().getMessages();
        this.entityCheckTypeProvider = new EntityCheckTypeProvider(wgrpPlugin);
        paramsVersionCheck = check;
    }

    @Override
    public boolean isPlayerListenerPermission(@NotNull Player player, @NotNull UtilPermissions perm) {
        return !player.hasPermission(perm.getPermissionName());
    }

    @Override
    public boolean isEntityListenerPermission(@NotNull Entity entity, @NotNull UtilPermissions perm) {
        return !entity.hasPermission(perm.getPermissionName());
    }

    /**
     * Initializes all used NMS classes, constructor fields, and methods.
     * Returns {@code true} if everything went successfully and version marked as compatible,
     * {@code false} if anything went wrong or version not marked as compatible.
     *
     * @return {@code true} if server version compatible, {@code false} if not
     */
    public boolean isVersionSupported() {
        final String minecraftVersion = Bukkit.getServer().getMinecraftVersion();
        try {
            final long time = System.currentTimeMillis();
            if (SUPPORTED_VERSION.contains(minecraftVersion)) {
                wgrpPlugin.getLogger().info("Loaded NMS hook in " + (System.currentTimeMillis() - time) + "ms");
                wgrpPlugin.getLogger().info(String.format("Current support versions range %s", SUPPORTED_VERSION_RANGE));
                return true;
            } else {
                wgrpPlugin.getLogger().info(
                        "No compatibility issue was found, but this plugin version does not claim to support your server package (" + minecraftVersion + ").");
            }
        } catch (Exception ignored) {
            if (SUPPORTED_VERSION.contains(minecraftVersion)) {
                wgrpPlugin.getLogger().severe(
                        "Your server version is marked as compatible, but a compatibility issue was found. Please report the error below (include your server version & fork too)");
            } else {
                wgrpPlugin.getLogger().severe(
                        "Your server version is completely unsupported. This plugin version only " +
                                "supports " + SUPPORTED_VERSION_RANGE + ". Disabling.");
            }
        }
        return false;
    }

    @Override
    public void notify(Player player, String playerName, String senderCommand, String regionName) {
        if (regionName == null) {
            return;
        }
        if (ConfigFields.IS_SPY_COMMAND_NOTIFY_ADMIN_ENABLE.getBoolean(wgrpPlugin.getWgrpPaperBase()) && this.isPlayerListenerPermission(
                player,
                UtilPermissions.REGION_PROTECT_NOTIFY_ADMIN
        )) {
            final String cmd = ConfigFields.SPY_COMMAND_LIST.get(wgrpPlugin.getWgrpPaperBase()).toString();
            if (cmd.contains(senderCommand.toLowerCase()) &&
                    ConfigFields.IS_SPY_COMMAND_NOTIFY_ADMIN_PLAY_SOUND_ENABLE.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
                player.playSound(
                        player.getLocation(),
                        ConfigFields.SPY_COMMAND_NOTIFY_PLAY_SOUND_TYPE
                                .get(wgrpPlugin.getWgrpPaperBase()).toString().toLowerCase(), 1, 1
                );
                messages.get("messages.Notify.sendAdminInfoIfUsedCommandInRG")
                        .replace("<player>", playerName)
                        .replace("<cmd>", cmd)
                        .replace("<region>", regionName).send(player);
            }
        }
    }

    @Override
    public void notify(String playerName, String senderCommand, String regionName) {
        if (regionName == null) {
            return;
        }
        if (ConfigFields.IS_SPY_COMMAND_NOTIFY_CONSOLE_ENABLE.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            final String cmd = ConfigFields.SPY_COMMAND_LIST.get(wgrpPlugin.getWgrpPaperBase()).toString();
            if (cmd.contains(senderCommand.toLowerCase())) {
                final ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
                messages.get("messages.Notify.sendAdminInfoIfUsedCommandInRG")
                        .replace("<player>", playerName)
                        .replace("<cmd>", cmd)
                        .replace("<region>", regionName).send(consoleSender);
            }
        }
    }

    /**
     * Send a notification to the administrator if a Player attempts to interact with a region from WorldGuard.
     *
     * @param admin         message for an admin who destroys a region.
     * @param suspectPlayer object player for method.
     * @param suspectName   player name who interacting with a region.
     * @param action        get the actions.
     * @param regionName    region name.
     * @param x             position of block.
     * @param y             position of block.
     * @param z             position of block.
     * @param world         position of block in the world.
     */
    public void notifyIfActionInRegion(
            Player admin,
            Player suspectPlayer,
            String suspectName,
            String action,
            String regionName,
            double x,
            double y,
            double z,
            String world
    ) {
        if (this.isPlayerListenerPermission(suspectPlayer, UtilPermissions.SPY_INSPECT_FOR_SUSPECT)
                && ConfigFields.IS_SPY_COMMAND_NOTIFY_ADMIN_ENABLE.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            messages.get("messages.Notify.sendAdminInfoIfActionInRegion")
                    .replace("<player>", suspectName)
                    .replace("<action>", action)
                    .replace("<region>", regionName)
                    .replace("<x>", String.valueOf(x))
                    .replace("<y>", String.valueOf(y))
                    .replace("<z>", String.valueOf(z))
                    .replace("<world>", world).send(admin);
        }
    }

    public void entityCheck(Cancellable cancellable, Entity entity, @NotNull Entity checkEntity) {
        if (wgrpPlugin.getRegionAdapter().checkStandingRegion(checkEntity.getLocation(),
                wgrpPlugin.getConfigLoader().getConfig().getRegionProtectMap())) {
            if (entity instanceof Player player) {
                if (wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                    entityCheck(cancellable, player);
                }
            } else {
                entityCheck(cancellable, checkEntity);
            }
        }
    }

    private void entityCheck(Cancellable cancellable, Entity checkEntity) {
        final EntityCheckType<Entity, EntityType> entityCheckType = entityCheckTypeProvider.getCheck(checkEntity);
        if (entityCheckType.check(checkEntity)) {
            cancellable.setCancelled(true);
        }
    }

    public void updateFile(@NotNull final WorldGuardRegionProtectPaperPlugin wgrpPlugin,
                           final @NotNull File currentFile, ConfigType configType, String lang) {
        if(ConfigType.CONFIG.equals(configType)) {
            final Path renameOldFile = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(),
                    "config-old-" + paramsVersionCheck.getSimpleDateFormat() + ".yml").toPath();
            try {
                Files.move(currentFile.toPath(), renameOldFile, StandardCopyOption.REPLACE_EXISTING);
                wgrpPlugin.getLogger().info("Old config file is renamed to: " + renameOldFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpPlugin.getWgrpPaperBase().saveResource("config.yml", true);
        } else if(ConfigType.LANG.equals(configType)) {
            final Path renameOldLang = new File(wgrpPlugin.getWgrpPaperBase().getDataFolder(),
                    "lang/" + lang + "-old-" + paramsVersionCheck.getSimpleDateFormat() + ".yml").toPath();
            try {
                Files.move(currentFile.toPath(), renameOldLang, StandardCopyOption.REPLACE_EXISTING);
                wgrpPlugin.getLogger().info("Old lang file is renamed to: " + renameOldLang);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            wgrpPlugin.getWgrpPaperBase().saveResource("lang/" + lang + ".yml", true);
        }
    }

}
