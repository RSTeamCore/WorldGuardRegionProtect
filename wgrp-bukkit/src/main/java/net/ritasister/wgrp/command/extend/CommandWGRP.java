package net.ritasister.wgrp.command.extend;

import lombok.Getter;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.config.Config;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class CommandWGRP extends AbstractCommand {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    @Getter
    private final Config config;

    @Getter
    private final Container messages;


    public CommandWGRP(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        super(UtilCommandList.WGRP.getCommand(), wgrpBukkitPlugin);
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
        this.messages = wgrpBukkitPlugin.getConfigLoader().getMessages();
    }

    @SneakyThrows
    @SubCommand(
            name = "reload",
            permission = UtilPermissions.COMMAND_WGRP_RELOAD_CONFIGS,
            description = "")
    public void wgrpReload(@NotNull CommandSender sender, String[] args) {
        long timeInitStart = System.currentTimeMillis();

        config.reload();
        wgrpBukkitPlugin.getConfigLoader().initConfig(wgrpBukkitPlugin.getWgrpBukkitBase());

        long timeReload = (System.currentTimeMillis() - timeInitStart);
        messages.get("messages.Configs.configReloaded").replace("<time>", timeReload).send(sender);
    }

    @SubCommand(
            name = "about",
            aliases = {"credits", "authors"},
            description = "seeing info about authors.")
    public void wgrpAbout(@NotNull CommandSender sender, String[] args) {
        wgrpBukkitPlugin.messageToCommandSender(sender, """
                            <aqua>========<dark_gray>[<red>WorldGuardRegionProtect<dark_gray>]<aqua>========
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
            Map<String, List<String>> rgMap = config.getRegionProtectMap();
            if(sender instanceof Player) {
                Player player = ((Player) sender).getPlayer();
                String region = args[0];
                String world = Objects.requireNonNull(player).getLocation().getWorld().getName();
                boolean isWorldValid = false;
                boolean isRegionValid = false;
                if(args.length == 2) world = args[1];
                for(World w : Bukkit.getWorlds()) if(w.getName().equalsIgnoreCase(world)) isWorldValid = true;
                if(wgrpBukkitPlugin.getRsRegion().getProtectRegionName(player.getLocation()).equalsIgnoreCase(region)) isRegionValid = true;
                if(!isWorldValid) {
                    messages.get("messages.regionManagement.invalidWorld").replace("<world>", world).send(sender);
                    return;
                }
                if(!isRegionValid) {
                    messages.get("messages.regionManagement.invalidRegion").replace("<region>", region).send(sender);
                    return;
                }
                List<String> newRegionList = new ArrayList<>();
                if(rgMap.containsKey(world) && !rgMap.get(world).contains(region)) newRegionList.addAll(rgMap.get(world));
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                config.setRegionProtectMap(rgMap);
                messages.get("messages.regionManagement.add").replace("<region>", region).send(sender);
            } else if(args.length == 2) {
                String region = args[0];
                String world = args[1];
                List<String> newRegionList = new ArrayList<>();
                if(rgMap.containsKey(world)) newRegionList.addAll(rgMap.get(world));
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                config.setRegionProtectMap(rgMap);
                messages.get("messages.regionManagement.add").replace("<region>", region).send(sender);
            } else messages.get("messages.usage.addRegionFromConsole").send(sender);
        } else messages.get("messages.usage.wgrpUseHelp").send(sender);
    }

    @SubCommand(
            name = "removeregion",
            aliases = {"removerg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_REMOVEREGION,
            description = "remove the region from the config to remove the protection.")
    public void wgrpRemoveRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if(args.length == 1 || args.length == 2) {
            Map<String, List<String>> rgMap = config.getRegionProtectMap();
            if(sender instanceof Player) {
                String region = args[0];
                String world = Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName();
                if(args.length == 2) world = args[1];
                if(rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                    List<String> newRegionList = new ArrayList<>(rgMap.get(world));
                    newRegionList.remove(region);
                    rgMap.put(world, newRegionList);
                    config.setRegionProtectMap(rgMap);
                    messages.get("messages.regionManagement.remove").replace("<region>", region).send(sender);
                } else messages.get("messages.regionManagement.notContains").replace("<region>", region).send(sender);
            } else {
                if(args.length == 2) {
                    String region = args[0];
                    String world = args[1];
                    if(rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
                        List<String> newRegionList = new ArrayList<>(rgMap.get(world));
                        newRegionList.remove(region);
                        rgMap.put(world, newRegionList);
                        config.setRegionProtectMap(rgMap);
                        messages.get("messages.regionManagement.remove").replace("<region>", region).send(sender);
                    } else messages.get("messages.regionManagement.notContains").replace("<region>", region).send(sender);
                } else messages.get("messages.regionManagement.removeRegionFromConsole").send(sender);
            }
        } else messages.get("messages.usage.wgrpUseHelp").send(sender);
    }

    @SubCommand(
            name = "help",
            description = "for seen helping.")
    public void wgrpHelp(CommandSender sender, String[] args) {
        List<Component> messages = new ArrayList<>(this.messages.get("messages.usage.title").replace("%command%", "/wgrp").toComponentList(false));
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);
                String tabArgs = String.join(" ", sub.tabArgs());
                messages.addAll(this.messages.get("messages.usage.format")
                        .replace("%command%", "/wgrp")
                        .replace("%alias%", sub.name())
                        .replace("%description%", sub.description())
                        .replace("%tabArgs%", tabArgs.isBlank() ? "" : tabArgs + " ")
                        .toComponentList(false));
            }
        } for (Component message : messages) {
            sender.sendMessage(message);
        }
    }

    @SubCommand(
            name = "spy",
            permission = UtilPermissions.COMMAND_SPY_INSPECT_ADMIN,
            description = "spy for who interact with region.")
    public void wgrpSpy(@NotNull CommandSender sender, String[] args) {
        @NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
        if (wgrpBukkitPlugin.getSpyLog().contains(uniqueId)) {
            wgrpBukkitPlugin.getSpyLog().remove(uniqueId);
            sender.sendMessage(Component.text("You are disable spy logging!"));
        } else {
            wgrpBukkitPlugin.getSpyLog().add(uniqueId);
            sender.sendMessage(Component.text("You are enable spy logging!"));
        }
    }
}
