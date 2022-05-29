package net.ritasister.wgrp.command.extend;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.command.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.Bukkit;
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

    @SubCommand(
            name = "reload",
            description = Message.subCommands_reload,
            permission = UtilPermissions.COMMAND_WGRP_RELOAD_CONFIGS)
    public void wgrpReload(@NotNull CommandSender sender, String[] args) {
        long timeInitStart = System.currentTimeMillis();

        wgRegionProtect.getUtilConfig().getConfig().reload();
        wgRegionProtect.getUtilConfig().loadMessages(wgRegionProtect);

        long timeReload = (System.currentTimeMillis() - timeInitStart);
        Message.Configs_configReloaded.replace("<time>", String.valueOf(timeReload)).send(sender);
    }

    @SubCommand(
            name = "about",
            aliases = {"credits", "authors"},
            description = Message.subCommands_about)
    public void wgrpAbout(@NotNull CommandSender sender, String[] args) {
        sender.sendMessage(wgRegionProtect.getChatApi().getColorCode("""
									&b======&8[&cWorldGuardRegionProtect&8]&b======
									&eHi! If you need help from this plugin,
									&eyou can contact with me on:
									&6 https://www.spigotmc.org/resources/81321/
									&eBut if you find any error or you want send
									&eme any idea for this plugin so you can create
									&eissues on github:
									&6 https://github.com/RSTeamCore/WorldGuardRegionProtect
									&5Your welcome!
									"""));
    }

    @SubCommand(
            name = "addregion",
            aliases = {"addrg"},
            tabArgs = "<region> [world]",
            description = Message.subCommands_addRegion,
            permission = UtilPermissions.COMMAND_ADDREGION)
    public void wgrpAddRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if(args.length == 1 || args.length == 2) {
            HashMap<String, List<String>> rgMap = wgRegionProtect.getUtilConfig().getConfig().getRegionProtectMap();
            if(sender instanceof Player) {
                String region = args[0];
                String world = Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName();
                ArrayList<String> newRegionList = new ArrayList<>();
                if(rgMap.containsKey(world) && !rgMap.get(world).contains(region)) newRegionList.addAll(rgMap.get(world));
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                Message.regionManagement_add.replace("<region>", region).send(sender);
            } else if(args.length == 2) {
                    String region = args[0];
                    String world = args[1];
                    ArrayList<String> newRegionList = new ArrayList<>();
                    if(rgMap.containsKey(world)) newRegionList.addAll(rgMap.get(world));
                    newRegionList.add(region);
                    rgMap.put(world, newRegionList);
                    wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                    Message.regionManagement_add.replace("<region>", region).send(sender);
                } else sender.sendMessage("Usage: /wgrp addregion [region] [world]");
        } else Message.usage_wgrpUseHelp.send(sender);
    }

    @SubCommand(
            name = "removeregion",
            aliases = {"removerg"},
            tabArgs = "<region> [world]",
            description = Message.subCommands_removeRegion,
            permission = UtilPermissions.COMMAND_ADDREGION)
    public void wgrpRemoveRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if(args.length == 1 || args.length == 2) {
            HashMap<String, List<String>> rgMap = wgRegionProtect.getUtilConfig().getConfig().getRegionProtectMap();
            if(sender instanceof Player) {
                String region = args[0];
                String world = Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName();
                ArrayList<String> newRegionList = new ArrayList<>();
                if(rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                    newRegionList.addAll(rgMap.get(world));
                    newRegionList.remove(region);
                    rgMap.put(world, newRegionList);
                    wgRegionProtect.getUtilConfig().getConfig().setRegionProtectMap(rgMap);
                    Message.regionManagement_remove.replace("<region>", region).send(sender);
                } else Message.regionManagement_notContains.replace("<region>", region).send(sender);
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
                        Message.regionManagement_remove.replace("<region>", region).send(sender);
                    } else Message.regionManagement_notContains.replace("<region>", region).send(sender);
                } else sender.sendMessage("Usage: /wgrp removeregion [region] [world]");
            }
        } else Message.usage_wgrpUseHelp.send(sender);
    }

    @SubCommand(
            name = "help",
            description = Message.subCommands_help)
    public void wgrpHelp(CommandSender sender, String[] args) {
        ArrayList<String> messages = new ArrayList<>(Message.usage_title.replace("%command%", "/wgrp").toList());
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);
                messages.addAll(Message.usage_format.replace("%command%", "/wgrp").replace("%alias%", sub.name()).
                        replace("%description%", sub.description().toString()).toList());
            }
        } for (String message : messages) {
            sender.sendMessage(message);
        }
    }

    @SubCommand(
            name = "spy",
            description = Message.subCommands_spy,
            permission = UtilPermissions.COMMAND_SPY_INSPECT_ADMIN)
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