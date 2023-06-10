package net.ritasister.wgrp.listener;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.event.player.PlayerLoomPatternSelectEvent;
import io.papermc.paper.event.player.PlayerStonecutterRecipeSelectEvent;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ToolsProtect implements Listener {

    private final WGRPContainer wgrpContainer;

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyPlayerTakeLecternBook(@NotNull PlayerTakeLecternBookEvent e) {
        Player player = e.getPlayer();
        Location location = e.getLectern().getLocation();
        if (wgrpContainer.getConfig().isDenyTakeLecternBook()) {
            if (wgrpContainer.getRsRegion().checkStandingRegion(location, wgrpContainer.getConfig().getRegionProtectMap())
                    && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyStonecutterRecipeSelect(@NotNull PlayerStonecutterRecipeSelectEvent e) {
        Player player = e.getPlayer();
        Location location = e.getStonecutterInventory().getLocation();
        if (wgrpContainer.getConfig().isDenyStonecutterRecipeSelect()) {
            if (wgrpContainer.getRsRegion().checkStandingRegion(Objects.requireNonNull(location), wgrpContainer.getConfig().getRegionProtectMap())
                    && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void denyLoomPatternSelect(@NotNull PlayerLoomPatternSelectEvent e) {
        Player player = e.getPlayer();
        Location location = e.getLoomInventory().getLocation();
        if (wgrpContainer.getConfig().isDenyLoomPatternSelect()) {
            if (wgrpContainer.getRsRegion().checkStandingRegion(
                    Objects.requireNonNull(location),
                    wgrpContainer.getConfig().getRegionProtectMap()
            )
                    && !wgrpContainer.getRsApi().isPlayerListenerPermission(player, UtilPermissions.REGION_PROTECT)) {
                e.setCancelled(true);
            }
        }
    }

}
