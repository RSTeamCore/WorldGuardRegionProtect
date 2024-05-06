package wgrp.listener;

import io.papermc.paper.event.player.PlayerLoomPatternSelectEvent;
import io.papermc.paper.event.player.PlayerStonecutterRecipeSelectEvent;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.util.config.Config;

import java.util.Objects;

public class ToolsProtect implements Listener {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final Config config;

    public ToolsProtect(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerTakeLecternBook(@NotNull PlayerTakeLecternBookEvent e) {
        Player player = e.getPlayer();
        Location location = e.getLectern().getLocation();
        if (config.isDenyTakeLecternBook()) {
            if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(location, config.getRegionProtectMap())
                    && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStonecutterRecipeSelect(@NotNull PlayerStonecutterRecipeSelectEvent e) {
        Player player = e.getPlayer();
        Location location = e.getStonecutterInventory().getLocation();
        if (config.isDenyStonecutterRecipeSelect()) {
            if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(
                    Objects.requireNonNull(location),
                    config.getRegionProtectMap()
            )
                    && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyLoomPatternSelect(@NotNull PlayerLoomPatternSelectEvent e) {
        Player player = e.getPlayer();
        Location location = e.getLoomInventory().getLocation();
        if (config.isDenyLoomPatternSelect()) {
            if (wgrpBukkitPlugin.getRegionAdapter().checkStandingRegion(
                    Objects.requireNonNull(location),
                    config.getRegionProtectMap()
            )
                    && wgrpBukkitPlugin.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

}
