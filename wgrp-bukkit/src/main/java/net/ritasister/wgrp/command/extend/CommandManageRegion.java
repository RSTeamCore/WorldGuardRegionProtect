package net.ritasister.wgrp.command.extend;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.UtilConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandManageRegion extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;
    private final UtilConfig utilConfig;

    public CommandManageRegion(@NotNull final WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.getCommand(), wgRegionProtect);
        this.wgRegionProtect = wgRegionProtect;
        this.utilConfig = wgRegionProtect.getUtilConfig();
    }

    @SubCommand(
            name = "addregion",
            aliases = {"addrg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_ADDREGION,
            description = "add a region to the config to protect.")
    public void wgrpAddRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length == 1 || args.length == 2) {
            Map<String, List<String>> rgMap = utilConfig.getConfig().getRegionProtectMap();
            if (sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                String region = args[0];
                String world = Objects.requireNonNull(player).getLocation().getWorld().getName();
                boolean isWorldValid = false;
                boolean isRegionValid = false;
                if (args.length == 2) {
                    world = args[1];
                }
                for (World w : Bukkit.getWorlds()) {
                    if (w.getName().equalsIgnoreCase(world)) {
                        isWorldValid = true;
                    }
                }
                if (wgRegionProtect.getRsRegion().getProtectRegionName(player.getLocation()).equalsIgnoreCase(region)) {
                    isRegionValid = true;
                }
                if (!isWorldValid) {
                    utilConfig.getMessages().get("messages.regionManagement.invalidWorld").replace(
                            "<world>",
                            world
                    ).send(sender);
                    return;
                }
                if (!isRegionValid) {
                    utilConfig.getMessages().get("messages.regionManagement.invalidRegion").replace(
                            "<region>",
                            region
                    ).send(sender);
                    return;
                }
                List<String> newRegionList = new ArrayList<>();
                if (rgMap.containsKey(world) && !rgMap.get(world).contains(region)) {
                    newRegionList.addAll(rgMap.get(world));
                }
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                utilConfig.getConfig().setRegionProtectMap(rgMap);
                wgRegionProtect
                        .getUtilConfig()
                        .getMessages()
                        .get("messages.regionManagement.add")
                        .replace("<region>", region)
                        .send(sender);
            } else if (args.length == 2) {
                String region = args[0];
                String world = args[1];
                ArrayList<String> newRegionList = new ArrayList<>();
                if (rgMap.containsKey(world)) {
                    newRegionList.addAll(rgMap.get(world));
                }
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                utilConfig.getConfig().setRegionProtectMap(rgMap);
                wgRegionProtect
                        .getUtilConfig()
                        .getMessages()
                        .get("messages.regionManagement.add")
                        .replace("<region>", region)
                        .send(sender);
            } else {
                utilConfig.getMessages().get("messages.usage.addRegionFromConsole").send(sender);
            }
        } else {
            utilConfig.getMessages().get("messages.usage.wgrpUseHelp").send(sender);
        }
    }

    @SubCommand(
            name = "removeregion",
            aliases = {"removerg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_REMOVEREGION,
            description = "remove the region from the config to remove the protection.")
    public void wgrpRemoveRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length == 1 || args.length == 2) {
            Map<String, List<String>> rgMap = utilConfig.getConfig().getRegionProtectMap();
            if (sender instanceof Player) {
                String region = args[0];
                String world = Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName();
                if (args.length == 2) {
                    world = args[1];
                }
                List<String> newRegionList = new ArrayList<>();
                if (rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                    newRegionList.addAll(rgMap.get(world));
                    newRegionList.remove(region);
                    rgMap.put(world, newRegionList);
                    utilConfig.getConfig().setRegionProtectMap(rgMap);
                    utilConfig.getMessages().get("messages.regionManagement.remove").replace(
                            "<region>",
                            region
                    ).send(sender);
                } else {
                    utilConfig.getMessages().get("messages.regionManagement.notContains").replace(
                            "<region>",
                            region
                    ).send(sender);
                }
            } else {
                if (args.length == 2) {
                    String region = args[0];
                    String world = args[1];
                    List<String> newRegionList = new ArrayList<>();
                    if (rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                        newRegionList.addAll(rgMap.get(world));
                        newRegionList.remove(region);
                        rgMap.put(world, newRegionList);
                        utilConfig.getConfig().setRegionProtectMap(rgMap);
                        utilConfig.getMessages().get("messages.regionManagement.remove").replace(
                                "<region>",
                                region
                        ).send(sender);
                    } else {
                        utilConfig.getMessages().get("messages.regionManagement.notContains").replace(
                                "<region>",
                                region
                        ).send(sender);
                    }
                } else {
                    utilConfig.getMessages().get("messages.regionManagement.removeRegionFromConsole").send(
                            sender);
                }
            }
        } else {
            utilConfig.getMessages().get("messages.usage.wgrpUseHelp").send(sender);
        }
    }

}
