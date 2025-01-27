package net.ritasister.wgrp.command.extend;

import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.file.config.Config;
import net.ritasister.wgrp.util.file.config.ConfigLoader;
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

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private final Config config;
    private final ConfigLoader configLoader;

    public CommandWGRP(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        super(UtilCommandList.WGRP.getCommand(), wgrpPlugin);
        this.wgrpPlugin = wgrpPlugin;
        this.config = wgrpPlugin.getConfigLoader().getConfig();
        configLoader = wgrpPlugin.getConfigLoader();
    }

    @SubCommand(
            name = "reload",
            permission = UtilPermissions.COMMAND_WGRP_RELOAD_CONFIGS,
            description = "reload config and lang file.")
    public void wgrpReload(@NotNull CommandSender sender) {
        final long timeInitStart = System.currentTimeMillis();
        final long timeReload = System.currentTimeMillis() - timeInitStart;
        wgrpPlugin.getConfigLoader().initConfig(wgrpPlugin);
        configLoader.getMessages().get("messages.Configs.configReloaded").replace("<time>", timeReload).send(sender);
    }

    @SubCommand(
            name = "about",
            aliases = {"credits", "authors"},
            description = "seeing info about authors.")
    public void wgrpAbout(@NotNull CommandSender sender) {
        wgrpPlugin.messageToCommandSender(sender, """
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
            permission = UtilPermissions.COMMAND_ADD_REGION,
            description = "add a region to the config to protect.")
    public void wgrpAddRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length == 1 || args.length == 2) {
            final Map<String, List<String>> rgMap = config.getRegionProtectMap();
            if (sender instanceof Player player) {
                String world = player.getLocation().getWorld().getName();
                    final String region = args[0];
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
                    if (wgrpPlugin.getRegionAdapter().getProtectRegionName(player.getLocation()).equalsIgnoreCase(region)) {
                        isRegionValid = true;
                    }
                    if (rgMap.containsKey(world) && rgMap.get(world) != null && rgMap.get(world).contains(region)) {
                        configLoader.getMessages().get("messages.regionManagement.alreadyProtected").replace("<region>", region).send(sender);
                        return;
                    }
                    if (!isWorldValid) {
                        configLoader.getMessages().get("messages.regionManagement.invalidWorld").replace("<world>", world).send(sender);
                        return;
                    }
                    if (!isRegionValid) {
                        configLoader.getMessages().get("messages.regionManagement.invalidRegion").replace("<region>", region).send(sender);
                        return;
                    }
                    final List<String> newRegionList = new ArrayList<>();
                    if (rgMap.containsKey(world) && !rgMap.get(world).contains(region)) {
                        newRegionList.addAll(rgMap.get(world));
                    }
                    newRegionList.add(region);
                    rgMap.put(world, newRegionList);
                    config.setRegionProtectMap(rgMap);
                    configLoader.getMessages().get("messages.regionManagement.add").replace("<region>", region).send(sender);
            } else if (args.length == 2) {
                final String region = args[0];
                final String world = args[1];
                final List<String> newRegionList = new ArrayList<>();
                if (rgMap.containsKey(world)) {
                    newRegionList.addAll(rgMap.get(world));
                }
                newRegionList.add(region);
                rgMap.put(world, newRegionList);
                config.setRegionProtectMap(rgMap);
                configLoader.getMessages().get("messages.regionManagement.add").replace("<region>", region).send(sender);
            } else {
                configLoader.getMessages().get("messages.usage.addRegionFromConsole").send(sender);
            }
        } else {
            configLoader.getMessages().get("messages.usage.wgrpUseHelp").send(sender);
        }
    }

    @SubCommand(
            name = "removeregion",
            aliases = {"removerg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_REMOVE_REGION,
            description = "remove the region from the config to remove the protection.")
    public void wgrpRemoveRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length == 1 || args.length == 2) {
            final Map<String, List<String>> rgMap = config.getRegionProtectMap();
            if (sender instanceof Player) {
                final String region = args[0];
                String world = Objects.requireNonNull(((Player) sender).getPlayer()).getLocation().getWorld().getName();
                if (args.length == 2) {
                    world = args[1];
                }
                getRgMap(sender, rgMap, region, world);
            } else {
                if (args.length == 2) {
                    final String region = args[0];
                    final String world = args[1];
                    getRgMap(sender, rgMap, region, world);
                } else {
                    configLoader.getMessages().get("messages.regionManagement.removeRegionFromConsole").send(sender);
                }
            }
        } else {
            configLoader.getMessages().get("messages.usage.wgrpUseHelp").send(sender);
        }
    }

    private void getRgMap(
            @NotNull final CommandSender sender,
            final @NotNull Map<String, List<String>> rgMap,
            final String region,
            final String world
    ) {
        if (rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
            final List<String> newRegionList = new ArrayList<>(rgMap.get(world));
            newRegionList.remove(region);
            rgMap.put(world, newRegionList);
            config.setRegionProtectMap(rgMap);
            configLoader.getMessages().get("messages.regionManagement.remove").replace("<region>", region).send(sender);
        } else {
            configLoader.getMessages().get("messages.regionManagement.notContains").replace("<region>", region).send(sender);
        }
    }

    @SubCommand(
            name = "help",
            description = "for seen helping.")
    public void wgrpHelp(CommandSender sender, String[] args) {
        final List<Component> messages = new ArrayList<>(this.configLoader.getMessages()
                .get("messages.usage.title")
                .replace("%command%", "/wgrp")
                .toComponentList(false));
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                final SubCommand sub = m.getAnnotation(SubCommand.class);
                final String tabArgs = String.join(" ", sub.tabArgs());
                messages.addAll(this.configLoader.getMessages().get("messages.usage.format")
                        .replace("%command%", "/wgrp")
                        .replace("%alias%", sub.name())
                        .replace("%description%", sub.description())
                        .replace("%tabArgs%", tabArgs.isBlank() ? "" : tabArgs + " ")
                        .toComponentList(false));
            }
        }
        for (Component message : messages) {
            sender.sendMessage(message);
        }
    }

    @SubCommand(
            name = "spy",
            permission = UtilPermissions.COMMAND_SPY_INSPECT_ADMIN,
            description = "spy for who interact with region.")
    public void wgrpSpy(@NotNull CommandSender sender, String[] args) {
        final @NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
        if (wgrpPlugin.getSpyLog().contains(uniqueId)) {
            wgrpPlugin.getSpyLog().remove(uniqueId);
            sender.sendMessage("You are disable spy logging!");
        } else {
            wgrpPlugin.getSpyLog().add(uniqueId);
            sender.sendMessage("You are enable spy logging!");
        }
    }

}
