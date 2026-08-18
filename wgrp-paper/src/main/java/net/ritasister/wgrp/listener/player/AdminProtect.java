package net.ritasister.wgrp.listener.player;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.worldguard.UtilCommandWE;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.rslibs.worldguard.CheckIntersection;
import net.ritasister.wgrp.util.config.field.ConfigFields;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class AdminProtect implements Listener {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    private static final Set<String> REGION_COMMANDS_NAME = Set.of(
            "/rg",
            "/region",
            "/regions",
            "/worldguard:rg",
            "/worldguard:region",
            "/worldguard:regions");
    private static final Set<String> REGION_EDIT_ARGS = Set.of("f", "flag");
    private static final Set<String> REGION_EDIT_ARGS_FLAGS = Set.of("-f", "-u", "-n", "-g", "-a");

    public AdminProtect(final WorldGuardRegionProtectPaperPlugin plugin) {
        this.wgrpPlugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    private void checkUpdateNotifyJoinPlayer(@NotNull PlayerJoinEvent e) {
        if (!wgrpPlugin.getPermissionCheck().hasPlayerPermission(e.getPlayer(), UtilPermissions.ADMIN_RIGHT)) {
            wgrpPlugin.getUpdateNotify().checkUpdateNotify(
                    wgrpPlugin.getBootstrap().getLoader().getDescription().getVersion(),
                    e.getPlayer(),
                    ConfigFields.UPDATE_CHECKER.asBoolean(wgrpPlugin),
                    ConfigFields.SEND_NO_UPDATE.asBoolean(wgrpPlugin));
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
                for (String list : wgrpPlugin.getConfigProvider().get().getRegionProtectMap().get(e.getPlayer().getLocation().getWorld().getName())) {
                    if (list.equalsIgnoreCase(string[2])) {
                        e.setCancelled(true);
                    }
                }
                for (String list : wgrpPlugin.getConfigProvider().get().getRegionProtectOnlyBreakAllowMap().get(
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

            if (ConfigFields.REGION_MESSAGE_PROTECT_WE.asBoolean(wgrpPlugin)) {
                wgrpPlugin.getMessageProvider().get().getMessage("messages.ServerMsg.wgrpMsgWe").send(e.getPlayer());
                e.setCancelled(true);
            }

            final String regionName = wgrpPlugin.getRegionAdapter().getProtectRegionNameBySelection(e.getPlayer());
            wgrpPlugin.getRsApi().notify(e.getPlayer(), e.getPlayer().getName(), cmd, regionName);
            wgrpPlugin.getRsApi().notify(e.getPlayer().getName(), cmd, regionName);
        }
    }

    private void checkRegionEditArgs1(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        checkRegionEditArgs(e, string, 3, 2, 3);
    }

    private void checkRegionEditArgs2(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        checkRegionEditArgs(e, string, 4, 2, 4);
    }

    private void checkRegionEditArgs3(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 5) {
            if (string[3].equalsIgnoreCase("-w")) {
                checkRegionEditArgs(e, string, 5, 3, 5);
            } else {
                checkRegionEditArgs(e, string, 5, 4, 5);
            }
        }
    }

    private void checkRegionEditArgs4(@NotNull PlayerCommandPreprocessEvent e, String @NotNull [] string) {
        if (string.length > 6) {
            int checkIdx = (string[4].equalsIgnoreCase("-w") || string[4].equalsIgnoreCase("-h")) ? 4 : 5;
            checkRegionEditArgsCustom(e, string, checkIdx);
        }
    }

    private void checkRegionEditArgs(@NotNull PlayerCommandPreprocessEvent e, @NotNull String @NotNull [] string, int minLength, int checkIndex, int valueIndex) {
        if (string.length <= minLength) {
            return;
        }

        final String checkArg = string[checkIndex].toLowerCase();
        final boolean matches = checkArg.equals("-w")
                || checkArg.equals("-h")
                || REGION_EDIT_ARGS.contains(checkArg)
                || REGION_EDIT_ARGS_FLAGS.contains(checkArg);

        if (!matches) {
            return;
        }

        final String worldName = e.getPlayer().getLocation().getWorld().getName();
        final String targetValue = string[valueIndex];

        // Проверка regionProtectMap
        var protectMap = wgrpPlugin.getConfigProvider().get().getRegionProtectMap().get(worldName);
        if (protectMap != null) {
            for (String list : protectMap) {
                if (list.equalsIgnoreCase(targetValue)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        // Проверка regionProtectOnlyBreakAllowMap
        var breakAllowMap = wgrpPlugin.getConfigProvider().get().getRegionProtectOnlyBreakAllowMap().get(worldName);
        if (breakAllowMap != null) {
            for (String list : breakAllowMap) {
                if (list.equalsIgnoreCase(targetValue)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    private void checkRegionEditArgsCustom(@NotNull PlayerCommandPreprocessEvent e,
                                           @NotNull String[] string,
                                           int checkIndex
    ) {
        if (string.length <= 6) {
            return;
        }

        String checkArg = string[checkIndex].toLowerCase();

        boolean matches = checkArg.equals("-w")
                || checkArg.equals("-h")
                || REGION_EDIT_ARGS.contains(checkArg)
                || REGION_EDIT_ARGS_FLAGS.contains(checkArg);

        if (!matches) {
            return;
        }

        String worldName = e.getPlayer().getLocation().getWorld().getName();
        String targetValue = string[6];

        // 1. Проверка regionProtectMap -> e.setCancelled(true)
        var protectMap = wgrpPlugin.getConfigProvider().get().getRegionProtectMap().get(worldName);
        if (protectMap != null) {
            for (String list : protectMap) {
                if (list.equalsIgnoreCase(targetValue)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }

        // 2. Проверка regionProtectOnlyBreakAllowMap -> e.setCancelled(false) (как в вашем 4-м методе)
        var breakAllowMap = wgrpPlugin.getConfigProvider().get().getRegionProtectOnlyBreakAllowMap().get(worldName);
        if (breakAllowMap != null) {
            for (String list : breakAllowMap) {
                if (list.equalsIgnoreCase(targetValue)) {
                    e.setCancelled(false);
                    return;
                }
            }
        }
    }
}

