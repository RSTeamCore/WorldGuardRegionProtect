package net.ritasister.wgrp.listener;

import io.papermc.paper.event.player.PlayerLoomPatternSelectEvent;
import io.papermc.paper.event.player.PlayerStonecutterRecipeSelectEvent;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Listens of all events where player can interact with tools, lectern, stonecutter or else.
 */
public class ToolsProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private final Config config;

    public ToolsProtect(final @NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
        this.config = wgrpPlugin.getConfigLoader().getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerTakeLecternBook(@NotNull PlayerTakeLecternBookEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getLectern().getLocation();
        if (ConfigFields.DENY_TAKE_LECTERN_BOOK.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                    && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStonecutterRecipeSelect(@NotNull PlayerStonecutterRecipeSelectEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getStonecutterInventory().getLocation();
        if (ConfigFields.DENY_STONECUTTER_RECIPE_SELECT.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(
                    Objects.requireNonNull(location),
                    config.getRegionProtectMap()
            )
                    && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyLoomPatternSelect(@NotNull PlayerLoomPatternSelectEvent e) {
        final Player player = e.getPlayer();
        final Location location = e.getLoomInventory().getLocation();
        if (ConfigFields.DENY_LOOM_PATTERN_SELECT.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
            if (wgrpPlugin.getRegionAdapter().checkStandingRegion(
                    Objects.requireNonNull(location),
                    config.getRegionProtectMap()
            )
                    && wgrpPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

}
