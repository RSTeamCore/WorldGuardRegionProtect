package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.Config;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.file.messages.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class AdminProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private final Config config;
    private final Messages messages;

    public AdminProtect(final WorldGuardRegionProtectPaperPlugin plugin, final Config config, final Messages messages) {
        wgrpPlugin = plugin;
        this.config = config;
        this.messages = messages;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void checkUpdateNotifyJoinPlayer(@NotNull PlayerJoinEvent e) {
        if (!wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.ADMIN_RIGHT)) {
            wgrpPlugin.getUpdateNotify().checkUpdateNotify(
                    wgrpPlugin.getWgrpPaperBase().getPluginMeta().getVersion(),
                    e.getPlayer(),
                    ConfigFields.UPDATE_CHECKER.getBoolean(wgrpPlugin.getWgrpPaperBase()),
                    ConfigFields.SEND_NO_UPDATE.getBoolean(wgrpPlugin.getWgrpPaperBase()));
        }
    }

}

