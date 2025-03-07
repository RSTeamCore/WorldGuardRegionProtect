package net.ritasister.wgrp.listener.player;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.rslibs.wg.CheckIntersection;
import net.ritasister.wgrp.util.file.config.Config;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.file.config.ConfigLoader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class AdminProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private final Config config;
    private final ConfigLoader configLoader;

    static final Set<String> REGION_COMMANDS_NAME = Set.of(
            "/rg",
            "/region",
            "/regions",
            "/worldguard:rg",
            "/worldguard:region",
            "/worldguard:regions");
    static final Set<String> REGION_EDIT_ARGS = Set.of("f", "flag");
    static final Set<String> REGION_EDIT_ARGS_FLAGS = Set.of("-f", "-u", "-n", "-g", "-a");

    public AdminProtect(final WorldGuardRegionProtectPaperPlugin plugin) {
        this.wgrpPlugin = plugin;
        this.config = wgrpPlugin.getConfigLoader().getConfig();
        this.configLoader = wgrpPlugin.getConfigLoader();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void checkUpdateNotifyJoinPlayer(@NotNull PlayerJoinEvent e) {
        if (!wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.ADMIN_RIGHT)) {
            wgrpPlugin.getUpdateNotify().checkUpdateNotify(
                    wgrpPlugin.getWgrpPaperBase().getDescription().getVersion(),
                    e.getPlayer(),
                    ConfigFields.UPDATE_CHECKER.getBoolean(wgrpPlugin.getWgrpPaperBase()),
                    ConfigFields.SEND_NO_UPDATE.getBoolean(wgrpPlugin.getWgrpPaperBase()));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void denyUseWEAndWGCommand(@NotNull PlayerCommandPreprocessEvent e) {
        final String[] string = e.getMessage().toLowerCase().split(" ");
        final String cmd = e.getMessage().split(" ")[0].toLowerCase();
        if (wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.REGION_PROTECT)) {
            checkIntersection(e, string, cmd);
            if (this.wgrpPlugin.getPlayerUtilWE().cmdWeCP(string[0])) {
                e.setMessage(e.getMessage().replace("-o", ""));
                if (!this.wgrpPlugin.getCheckIntersection().checkCPIntersection(e.getPlayer())) {
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
        final UtilCommandWE playerUtilWE = this.wgrpPlugin.getPlayerUtilWE();
        final CheckIntersection checkIntersection = this.wgrpPlugin.getCheckIntersection();

        if (playerUtilWE.cmdWe(string[0]) && !checkIntersection.checkIntersection(e.getPlayer())
                || playerUtilWE.cmdWeC(string[0]) && !checkIntersection.checkCIntersection(e.getPlayer(), string)
                || playerUtilWE.cmdWeP(string[0]) && !checkIntersection.checkPIntersection(e.getPlayer(), string)
                || playerUtilWE.cmdWeS(string[0]) && !checkIntersection.checkSIntersection(e.getPlayer(), string)
                || playerUtilWE.cmdWeU(string[0]) && !checkIntersection.checkUIntersection(e.getPlayer(), string)) {

            if (ConfigFields.REGION_MESSAGE_PROTECT_WE.getBoolean(wgrpPlugin.getWgrpPaperBase())) {
                configLoader.getMessages().get("messages.ServerMsg.wgrpMsgWe").send(e.getPlayer());
                e.setCancelled(true);
            }

            final String regionName = wgrpPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer());
            wgrpPlugin.getRsApi().notify(e.getPlayer(), e.getPlayer().getName(), cmd, regionName);
            wgrpPlugin.getRsApi().notify(e.getPlayer().getName(), cmd, regionName);
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

