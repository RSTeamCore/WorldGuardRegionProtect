package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.api.permissions.PermissionsCheck;
import net.ritasister.wgrp.api.regions.RegionAction;
import net.ritasister.wgrp.rslibs.api.config.Container;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckTypeProvider;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class RSApiImpl implements MessagingService<Player>, PermissionsCheck<Player, Entity> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final EntityCheckTypeProvider entityCheckTypeProvider;

    private final Container messages;

    public final static String SUPPORTED_VERSION_RANGE = "1.20.2 - 1.20.4";
    public final static List<String> SUPPORTED_VERSION = Arrays.asList("1.20.2", "1.20.3", "1.20.4");

    public RSApiImpl(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.messages = wgrpBukkitPlugin.getConfigLoader().getMessages();
        this.entityCheckTypeProvider = new EntityCheckTypeProvider(wgrpBukkitPlugin);
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
     * Initializes all used NMS classes, constructors, fields and methods.
     * Returns {@code true} if everything went successfully and version marked as compatible,
     * {@code false} if anything went wrong or version not marked as compatible.
     *
     * @return {@code true} if server version compatible, {@code false} if not
     */
    public boolean isVersionSupported() {
        String minecraftVersion = Bukkit.getServer().getMinecraftVersion();
        try {
            long time = System.currentTimeMillis();
            if (SUPPORTED_VERSION.contains(minecraftVersion)) {
                wgrpBukkitPlugin.getPluginLogger().info("Loaded NMS hook in " + (System.currentTimeMillis() - time) + "ms");
                wgrpBukkitPlugin.getPluginLogger().info(String.format(
                        "Current support versions range %s",
                        SUPPORTED_VERSION_RANGE
                ));
                return true;
            } else {
                wgrpBukkitPlugin.getPluginLogger().info(
                        "No compatibility issue was found, but this plugin version does not claim to support your server package (" + minecraftVersion + ").");
            }
        } catch (Exception ex) {
            if (SUPPORTED_VERSION.contains(minecraftVersion)) {
                wgrpBukkitPlugin.getPluginLogger().severe(
                        "Your server version is marked as compatible, but a compatibility issue was found. Please report the error below (include your server version & fork too)");
            } else {
                wgrpBukkitPlugin.getPluginLogger().severe(
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
        if (wgrpBukkitPlugin.getConfigLoader().getConfig().getSpyCommandNotifyAdminEnable() && this.isPlayerListenerPermission(
                player,
                UtilPermissions.REGION_PROTECT_NOTIFY_ADMIN)) {
            String cmd = wgrpBukkitPlugin.getConfigLoader().getConfig().getSpyCommandList().toString();
            if (cmd.contains(senderCommand.toLowerCase()) && wgrpBukkitPlugin
                    .getConfigLoader()
                    .getConfig()
                    .getSpyCommandNotifyAdminPlaySoundEnable()) {
                player.playSound(
                        player.getLocation(),
                        wgrpBukkitPlugin.getConfigLoader().getConfig().getSpyCommandNotifyAdminPlaySound().toLowerCase(),
                        1,
                        1
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
        if (wgrpBukkitPlugin.getConfigLoader().getConfig().getSpyCommandNotifyConsoleEnable()) {
            String cmd = wgrpBukkitPlugin.getConfigLoader().getConfig().getSpyCommandList().toString();
            if(cmd.contains(senderCommand.toLowerCase())) {
                ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
                messages.get("messages.Notify.sendAdminInfoIfUsedCommandInRG")
                        .replace("<player>", playerName)
                        .replace("<cmd>", cmd)
                        .replace("<region>", regionName).send(consoleSender);
            }
        }
    }

    /**
     * Send a notification to the administrator if Player attempts to interact with a region from WorldGuard.
     *
     * @param admin         message for an admin who destroys a region.
     * @param suspectPlayer object player for method.
     * @param suspectName   player name who interacting with a region.
     * @param action        get the actions.
     * @param regionName    region name.
     * @param x             position of block.
     * @param y             position of block.
     * @param z             position of block.
     * @param world         position of block in world.
     */
    public void notifyIfActionInRegion(
            Player admin,
            Player suspectPlayer,
            String suspectName,
            RegionAction action,
            String regionName,
            double x,
            double y,
            double z,
            String world
    ) {
        if (this.isPlayerListenerPermission(suspectPlayer, UtilPermissions.SPY_INSPECT_FOR_SUSPECT)
                && wgrpBukkitPlugin.getConfigLoader().getConfig().getSpyCommandNotifyAdminEnable()) {
            messages.get("messages.Notify.sendAdminInfoIfActionInRegion")
                    .replace("<player>", suspectName)
                    .replace("<action>", action.getAction())
                    .replace("<region>", regionName)
                    .replace("<x>", String.valueOf(x))
                    .replace("<y>", String.valueOf(y))
                    .replace("<z>", String.valueOf(z))
                    .replace("<world>", world).send(admin);
        }
    }

    public void entityCheck(Cancellable cancellable, Entity entity, @NotNull Entity checkEntity) {
        if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(
                checkEntity.getLocation(),
                wgrpBukkitPlugin.getConfigLoader().getConfig().getRegionProtectMap()
        )) {
            switch (entity) {
                case Player player when wgrpBukkitPlugin.getRsApi().isEntityListenerPermission(
                        entity,
                        UtilPermissions.REGION_PROTECT
                ) -> entityCheck(cancellable, checkEntity);
                case null, default -> entityCheck(cancellable, checkEntity);
            }
        }
    }

    private void entityCheck(Cancellable cancellable, Entity checkEntity) {
        EntityCheckType<Entity, EntityType> entityCheckType = entityCheckTypeProvider.getCheck(checkEntity);
        if (entityCheckType.check(checkEntity)) {
            cancellable.setCancelled(true);
        }
    }

}
