package net.ritasister.wgrp.listener;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.file.config.Config;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.file.messages.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class AdminProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private final Config config;
    private final Messages messages;

    static final Set<String> REGION_COMMANDS_NAME = Set.of(
            "/rg",
            "/region",
            "/regions",
            "/worldguard:rg",
            "/worldguard:region",
            "/worldguard:regions"
    );
    static final Set<String> REGION_EDIT_ARGS = Set.of("f", "flag");
    static final Set<String> REGION_EDIT_ARGS_FLAGS = Set.of("-f", "-u", "-n", "-g", "-a");

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

    @EventHandler(priority = EventPriority.LOW)
    private void denyUseWEAndWGCommand(@NotNull PlayerCommandPreprocessEvent e) {
        final String[] string = e.getMessage().toLowerCase().split(" ");
        final String cmd = e.getMessage().split(" ")[0].toLowerCase();
        if (wgrpPlugin.getRsApi().isPlayerListenerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            checkIntersection(e, string, cmd);
            if (this.wgrpPlugin.getPlayerUtilWE().cmdWeCP(string[0])) {
                e.setMessage(e.getMessage().replace("-o", ""));
                if (!this.wgrpPlugin.getCheckIntersection().checkCPIntersection(e.getPlayer(), string)) {
                    e.setCancelled(true);
                }
                wgrpPlugin.getRsApi().notify(e.getPlayer(), e.getPlayer().getName(), cmd, wgrpPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
                wgrpPlugin.getRsApi().notify(e.getPlayer().getName(), cmd, wgrpPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
            }
            if (REGION_COMMANDS_NAME.contains(string[0]) && string.length > 2) {
                for (String list : config.getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                    if (list.equalsIgnoreCase(string[2])) {
                        e.setCancelled(true);
                    }
                }
                for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                        e.getPlayer().getLocation()
                                .getWorld()
                                .getName())) {
                    if (list.equalsIgnoreCase(string[2])) {
                        e.setCancelled(true);
                    }
                }
                checkRegionEditArgs1(e, string);
                checkRegionEditArgs2(e, string);
                checkRegionEditArgs3(e, string);
                checkRegionEditArgs4(e, string);
            }
        }
    }

    private void checkIntersection(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string, String cmd) {
        if (this.wgrpPlugin.getPlayerUtilWE().cmdWe(string[0]) &&
                !this.wgrpPlugin.getCheckIntersection().checkIntersection(e.getPlayer())
                || this.wgrpPlugin.getPlayerUtilWE().cmdWeC(string[0]) &&
                !this.wgrpPlugin.getCheckIntersection().checkCIntersection(e.getPlayer(), string)
                || this.wgrpPlugin.getPlayerUtilWE().cmdWeP(string[0]) &&
                !this.wgrpPlugin.getCheckIntersection().checkPIntersection(e.getPlayer(), string)
                || this.wgrpPlugin.getPlayerUtilWE().cmdWeS(string[0]) &&
                !this.wgrpPlugin.getCheckIntersection().checkSIntersection(e.getPlayer(), string)
                || this.wgrpPlugin.getPlayerUtilWE().cmdWeU(string[0]) &&
                !this.wgrpPlugin.getCheckIntersection().checkUIntersection(e.getPlayer(), string)) {
            if (ConfigFields.REGION_MESSAGE_PROTECT_WE.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
                messages.get("messages.ServerMsg.wgrpMsgWe").send(e.getPlayer());
                e.setCancelled(true);
            }
            wgrpPlugin.getRsApi().notify(
                    e.getPlayer(), e.getPlayer().getName(),
                    cmd, wgrpPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
            wgrpPlugin.getRsApi().notify(
                    e.getPlayer().getName(),
                    cmd, wgrpPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer()));
        }
    }

    private void checkRegionEditArgs1(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 3 && REGION_EDIT_ARGS.contains(string[2].toLowerCase())
                || string.length > 3 && REGION_EDIT_ARGS_FLAGS.contains(string[2].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(
                    e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[3])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                            .getWorld()
                            .getName())) {
                if (list.equalsIgnoreCase(string[3])) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void checkRegionEditArgs2(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 4 && string[2].equalsIgnoreCase("-w")
                || string.length > 4 && REGION_EDIT_ARGS.contains(string[2].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(
                    e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[4])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                            .getWorld()
                            .getName())) {
                if (list.equalsIgnoreCase(string[4])) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void checkRegionEditArgs3(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 5 && string[3].equalsIgnoreCase("-w")
                || string.length > 5 && REGION_EDIT_ARGS.contains(string[4].toLowerCase())
                || string.length > 5 && REGION_EDIT_ARGS_FLAGS.contains(string[4].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(
                    e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[5])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                            .getWorld()
                            .getName())) {
                if (list.equalsIgnoreCase(string[5])) {
                    e.setCancelled(true);
                }
            }
        }
    }

    private void checkRegionEditArgs4(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 6 && string[4].equalsIgnoreCase("-w")
                || string.length > 6 && string[4].equalsIgnoreCase("-h")
                || string.length > 6 && REGION_EDIT_ARGS.contains(string[5].toLowerCase())
                || string.length > 6 && REGION_EDIT_ARGS_FLAGS.contains(string[5].toLowerCase())) {
            for (String list : config.getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                if (list.equalsIgnoreCase(string[6])) {
                    e.setCancelled(true);
                }
            }
            for (String list : config.getRegionProtectOnlyBreakAllowMap().get(
                    e.getPlayer().getLocation()
                            .getWorld()
                            .getName())) {
                if (list.equalsIgnoreCase(string[6])) {
                    e.setCancelled(false);
                }
            }
        }
    }

}

