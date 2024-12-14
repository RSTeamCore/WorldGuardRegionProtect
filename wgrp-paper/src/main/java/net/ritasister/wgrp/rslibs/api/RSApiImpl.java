package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.messaging.MessagingService;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckTypeProvider;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.file.messages.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public class RSApiImpl implements MessagingService<Player> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private final EntityCheckTypeProvider entityCheckTypeProvider;
    private final Messages messages;

    public RSApiImpl(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
        this.messages = wgrpPlugin.getConfigLoader().getMessages();
        this.entityCheckTypeProvider = new EntityCheckTypeProvider(wgrpPlugin);
    }

    @Override
    public void notify(Player player, String playerName, String senderCommand, String regionName) {
        if (regionName == null) {
            return;
        }
        if (ConfigFields.IS_SPY_COMMAND_NOTIFY_ADMIN_ENABLE.getBoolean(wgrpPlugin.getWgrpPaperBase()) && this.wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.REGION_PROTECT_NOTIFY_ADMIN)) {
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
    public void notifyIfActionInRegion(Player admin, Player suspectPlayer, String suspectName, String action, String regionName, double x, double y, double z, String world) {
        if (this.wgrpPlugin.getPermissionCheck().hasPlayerPermission(suspectPlayer, UtilPermissions.SPY_INSPECT_FOR_SUSPECT)
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
                if (wgrpPlugin.getPermissionCheck().hasPlayerPermission(player, UtilPermissions.REGION_PROTECT)) {
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

}
