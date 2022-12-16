package net.ritasister.wgrp.command.extend;

import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.command.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.*;

public class CommandWGRP extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandWGRP(@NotNull WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.getCommand(), wgRegionProtect);
        this.wgRegionProtect = wgRegionProtect;
    }

    @SneakyThrows
    @SubCommand(
            name = "reload",
            permission = UtilPermissions.COMMAND_WGRP_RELOAD_CONFIGS,
            description = "")
    public void wgrpReload(@NotNull CommandSender sender, String[] args) {
        long timeInitStart = System.currentTimeMillis();

        wgRegionProtect.getUtilConfig().getConfig().reload();
        wgRegionProtect.getUtilConfig().initConfig(wgRegionProtect, wgRegionProtect.getWGRPBukkitPlugin());

        long timeReload = (System.currentTimeMillis() - timeInitStart);
        wgRegionProtect.getUtilConfig().getMessages().get("messages.Configs.configReloaded").replace("<time>", String.valueOf(timeReload)).send(sender);
    }

    @SubCommand(
            name = "about",
            aliases = {"credits", "authors"},
            description = "seeing info about authors.")
    public void wgrpAbout(@NotNull CommandSender sender, String[] args) {
        wgRegionProtect.getRsApi().getMessageToCommandSender(sender, """
                            <aqua>======<dark_gray>[&cWorldGuardRegionProtect<dark_gray>]<aqua>======
                            <yellow>Hi! If you need help from this plugin,
                            <yellow>you can contact with me on:
                            <gold> https://www.spigotmc.org/resources/81321/
                            <yellow>But if you find any error or you want send
                            <yellow>me any idea for this plugin so you can create
                            <yellow>issues on github:
                            <gold> https://github.com/RSTeamCore/WorldGuardRegionProtect
                            <dark_purple>Your welcome!
                            """);
    }

    @SubCommand(
            name = "addregion",
            aliases = {"addrg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_ADDREGION,
            description = "add a region to the config to protect.")
    public void wgrpAddRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if(args.length == 1 || args.length == 2) {
            HashMap<String, List<String>> rgMap = wgRegionProtect.getUtilConfig().getConfig().getRegionProtectMap();
            if(sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                String region = args[0];
                String world = Objects.requireNonNull(player).getLocation().getWorld().getName();
                boolean isWorldValid = false;
                boolean isRegionValid = false;
                if(args.length == 2) world = args[1];
                for(World w : Bukkit.getWorlds()) if(w.getName().equalsIgnoreCase(world)) isWorldValid = true;
                if(wgRegionProtect.getRsRegion().getProtectRegionName(player.getLocation()).equalsIgnoreCase(region)) isRegionValid = true;
                if(!isWorldValid) {
                    wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.invalidWorld").replace("<world>", world).send(sender);
                    return;
                }
                if(!isRegionValid) {
                    wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.invalidRegion").replace("<region>", region).send(sender);
                    return;
                }
                ArrayList<String> newRegionList = new ArrayList<>();
                if(rgMap.containsKey(world) && !rgMap.get(world).contains(region)) newRegionList.addAll(rgMap.get(world));
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.add").replace("<region>", region).send(sender);
            } else if(args.length == 2) {
                    String region = args[0];
                    String world = args[1];
                    ArrayList<String> newRegionList = new ArrayList<>();
                    if(rgMap.containsKey(world)) newRegionList.addAll(rgMap.get(world));
                    newRegionList.add(region);
                    rgMap.put(world, newRegionList);
                    wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.add").replace("<region>", region).send(sender);
                } else wgRegionProtect.getUtilConfig().getMessages().get("messages.usage.addRegionFromConsole").send(sender);
        } else wgRegionProtect.getUtilConfig().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
    }

    @SubCommand(
            name = "removeregion",
            aliases = {"removerg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_REMOVEREGION,
            description = "remove the region from the config to remove the protection.")
    public void wgrpRemoveRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if(args.length == 1 || args.length == 2) {
            HashMap<String, List<String>> rgMap = wgRegionProtect.getUtilConfig().getConfig().getRegionProtectMap();
            if(sender instanceof Player) {
                String region = args[0];
                String world = Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName();
                if(args.length == 2) world = args[1];
                ArrayList<String> newRegionList = new ArrayList<>();
                if(rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                    newRegionList.addAll(rgMap.get(world));
                    newRegionList.remove(region);
                    rgMap.put(world, newRegionList);
                    wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                    wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.remove").replace("<region>", region).send(sender);
                } else wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.notContains").replace("<region>", region).send(sender);
            } else {
                if(args.length == 2) {
                    String region = args[0];
                    String world = args[1];
                    ArrayList<String> newRegionList = new ArrayList<>();
                    if(rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                        newRegionList.addAll(rgMap.get(world));
                        newRegionList.remove(region);
                        rgMap.put(world, newRegionList);
                        wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                        wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.remove").replace("<region>", region).send(sender);
                    } else wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.notContains").replace("<region>", region).send(sender);
                } else wgRegionProtect.getUtilConfig().getMessages().get("messages.regionManagement.removeRegionFromConsole").send(sender);
            }
        } else wgRegionProtect.getUtilConfig().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
    }

    @SubCommand(
            name = "help",
            description = "for seen helping.")
    public void wgrpHelp(CommandSender sender, String[] args) {
        ArrayList<String> messages = new ArrayList<>(
                wgRegionProtect.getUtilConfig().getMessages().get("messages.usage.title").replace("%command%", "/wgrp").toList());
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);
                String tabArgs = String.join(" ", sub.tabArgs());
                messages.addAll(wgRegionProtect.getUtilConfig().getMessages().get("messages.usage.format")
                        .replace("%command%", Component.text("/wgrp"))
                        .replace("%alias%", sub.name())
                        .replace("%description%", sub.description())
                        .replace("%tabArgs%", tabArgs.isBlank() ? "" : tabArgs + " ")
                        .toList());
            }
        } for (String message : messages) {
            sender.sendMessage(message);
        }
    }

    @SubCommand(
            name = "spy",
            permission = UtilPermissions.COMMAND_SPY_INSPECT_ADMIN,
            description = "spy for who interact with region.")
    public void wgrpSpy(@NotNull CommandSender sender, String[] args) {
        @NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
        if (wgRegionProtect.spyLog.contains(uniqueId)) {
            wgRegionProtect.spyLog.remove(uniqueId);
            sender.sendMessage("You are disable spy logging!");
        } else {
            wgRegionProtect.spyLog.add(uniqueId);
            sender.sendMessage("You are enable spy logging!");
        }
    }
}