package net.ritasister.wgrp.command.extend;

import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.api.manager.region.RegionAction;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import net.ritasister.wgrp.util.utility.CheckResult;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
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

    public CommandWGRP(@NotNull WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        super(wgrpPlugin);
        this.wgrpPlugin = wgrpPlugin;
        this.register(UtilCommandList.WGRP.getCommand());
    }

    @SubCommand(
            name = "reload",
            permission = UtilPermissions.COMMAND_WGRP_RELOAD_CONFIGS,
            description = "reload config and lang file.")
    public void wgrpReload(@NotNull CommandSender sender) {
        wgrpPlugin.getMessageProvider().getMessages().get("messages.Configs.configReloaded")
                .replace("<time>", wgrpPlugin.getConfigLoader().reload(wgrpPlugin)).send(sender);
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
            name = "protect",
            tabArgs = {"<region>"},
            permission = UtilPermissions.COMMAND_PROTECT,
            description = "Add a region to the config to protect.")
    public void wgrpProtect(@NotNull CommandSender sender, String @NotNull [] args) {
        handleRegionProtection(sender, args, RegionAction.ADD);
    }

    @SubCommand(
            name = "unprotect",
            tabArgs = {"<region>"},
            permission = UtilPermissions.COMMAND_UNPROTECT,
            description = "Remove a region protection from the config.")
    public void wgrpUnProtect(@NotNull CommandSender sender, String @NotNull [] args) {
        handleRegionProtection(sender, args, RegionAction.REMOVE);
    }

    private void handleRegionProtection(@NotNull CommandSender sender, String @NotNull [] args, RegionAction action) {
        if (args.length != 1) {
            wgrpPlugin.getMessageProvider().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
            return;
        }

        if (!(sender instanceof Player player)) {
            wgrpPlugin.getMessageProvider().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
            return;
        }

        final String region = args[0];
        final String world = player.getWorld().getName();

        final Map<String, List<String>> playerRgMap = wgrpPlugin.getConfigProvider().getConfig().getPlayerRegionProtectMap();
        final Map<String, List<String>> rgMap = wgrpPlugin.getConfigProvider().getConfig().getRegionProtectMap();

        final List<String> serverRegions = rgMap.computeIfAbsent(world, k -> new ArrayList<>());
        final boolean isServerProtected = serverRegions.contains(region);
        final boolean checkStanding = ConfigFields.CHECK_PLAYER_IN_STANDING_REGION.asBoolean(wgrpPlugin.getWgrpPaperBase());

        if (checkStanding && wgrpPlugin.getRegionAdapter().checkStandingRegion(player.getLocation())) {
            wgrpPlugin.getMessageProvider().getMessages()
                    .get("messages.regionManagement.notStandingInRegion")
                    .replace("<region>", region)
                    .send(sender);
            return;
        }

        if (!wgrpPlugin.getRegionAdapter().getProtectRegionName(player.getLocation()).equalsIgnoreCase(region)) {
            wgrpPlugin.getMessageProvider().getMessages()
                    .get("messages.regionManagement.invalidRegion")
                    .replace("<region>", region)
                    .send(sender);
            return;
        }

        if (wgrpPlugin.getRegionAdapter().isOwnerRegion(player.getLocation(), player.getUniqueId())) {
            wgrpPlugin.getMessageProvider().getMessages()
                    .get("messages.regionManagement.notOwnerRegion")
                    .replace("<region>", region)
                    .send(sender);
            return;
        }

        final List<String> playerRegions = playerRgMap.computeIfAbsent(world, k -> new ArrayList<>());

        if (action == RegionAction.ADD) {
            if (isServerProtected) {
                wgrpPlugin.getMessageProvider().getMessages()
                        .get("messages.regionManagement.restrictProtect")
                        .replace("<region>", region)
                        .send(sender);
                return;
            }
            if (playerRegions.contains(region)) {
                wgrpPlugin.getMessageProvider().getMessages()
                        .get("messages.regionManagement.alreadyProtected")
                        .replace("<region>", region)
                        .send(sender);
                return;
            }
            modifyRegionMap(playerRgMap, world, region, true);
            wgrpPlugin.getMessageProvider().getMessages()
                    .get("messages.regionManagement.add")
                    .replace("<region>", region)
                    .send(sender);

        } else {
            if (isServerProtected) {
                wgrpPlugin.getMessageProvider().getMessages()
                        .get("messages.regionManagement.restrictUnProtect")
                        .replace("<region>", region)
                        .send(sender);
                return;
            }
            if (!playerRegions.contains(region)) {
                wgrpPlugin.getMessageProvider().getMessages()
                        .get("messages.regionManagement.notContains")
                        .replace("<region>", region)
                        .send(sender);
                return;
            }
            modifyRegionMap(playerRgMap, world, region, false);
            wgrpPlugin.getMessageProvider().getMessages()
                    .get("messages.regionManagement.remove")
                    .replace("<region>", region)
                    .send(sender);
        }

        wgrpPlugin.getConfigProvider().getConfig().setPlayerRegionProtectMap(playerRgMap);
    }

    private void modifyRegionMap(@NotNull Map<String, List<String>> map, @NotNull String world, @NotNull String region, boolean action) {
        final List<String> regions = map.computeIfAbsent(world, k -> new ArrayList<>());
        if (action) {
            if (!regions.contains(region)) regions.add(region);
        } else {
            regions.remove(region);
        }
        map.put(world, regions);
    }

    @SubCommand(
            name = "addregion",
            aliases = {"addrg"},
            tabArgs = {"<region>", "[world]"},
            permission = UtilPermissions.COMMAND_ADD_REGION,
            description = "add a region to the config to protect.")
    public void wgrpAddRegion(@NotNull CommandSender sender, String @NotNull [] args) {
        if (args.length == 1 || args.length == 2) {
            final Map<String, List<String>> rgMap = wgrpPlugin.getConfigProvider().getConfig().getRegionProtectMap();
            if (sender instanceof Player) {
                final Check result = getCheck(sender, args).getCheck();
                if (result == null || result.region() == null || result.finalWorld() == null) {
                    return;
                }

                final String worldName = result.finalWorld().getName();
                if (rgMap.containsKey(worldName) && rgMap.get(worldName) != null && rgMap.get(worldName).contains(result.region())) {
                    wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.alreadyProtected").replace("<region>", result.region()).send(sender);
                    return;
                }

                final List<String> newRegionList = new ArrayList<>();
                if (rgMap.containsKey(worldName) && !rgMap.get(worldName).contains(result.region())) {
                    newRegionList.addAll(rgMap.get(worldName));
                }
                newRegionList.add(result.region());
                rgMap.put(worldName, newRegionList);
                wgrpPlugin.getConfigProvider().getConfig().setRegionProtectMap(rgMap);
                wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.add").replace("<region>", result.region()).send(sender);
            } else if (args.length == 2) {
                final String region = args[0];
                final Check result = getCheck(sender, args).getCheck();
                final List<String> newRegionList = new ArrayList<>();
                final String worldName = result.finalWorld().getName();
                if (rgMap.containsKey(worldName)) {
                    newRegionList.addAll(rgMap.get(worldName));
                }
                newRegionList.add(region);
                rgMap.put(worldName, newRegionList);
                wgrpPlugin.getConfigProvider().getConfig().setRegionProtectMap(rgMap);
                wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.add").replace("<region>", region).send(sender);
            } else {
                wgrpPlugin.getMessageProvider().getMessages().get("messages.usage.addRegionFromConsole").send(sender);
            }
        } else {
            wgrpPlugin.getMessageProvider().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
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
            final Map<String, List<String>> rgMap = wgrpPlugin.getConfigProvider().getConfig().getRegionProtectMap();
            if (sender instanceof Player) {
                final Check result = getCheck(sender, args).getCheck();
                if (result == null || result.region() == null || result.finalWorld() == null) {
                    return;
                }

                final String worldName = result.finalWorld().getName();
                getRgMap(sender, rgMap, result.region(), worldName);
            } else {
                if (args.length == 2) {
                    final String region = args[0];
                    final String world = args[1];
                    getRgMap(sender, rgMap, region, world);
                } else {
                    wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.removeRegionFromConsole").send(sender);
                }
            }
        } else {
            wgrpPlugin.getMessageProvider().getMessages().get("messages.usage.wgrpUseHelp").send(sender);
        }
    }

    private CheckResult getCheck(CommandSender sender, String @NotNull [] args) {
        if (args.length == 0 || args[0] == null || args[0].isEmpty()) {
            return CheckResult.error("Region name is missing or empty.");
        }

        final String regionName = args[0];
        final String worldName = (args.length >= 2) ? args[1] : null;

        final World world;
        if (worldName != null) {
            world = Bukkit.getWorlds().stream()
                    .filter(w -> w.getName().equalsIgnoreCase(worldName))
                    .findFirst()
                    .orElse(null);

            if (world == null) {
                wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.invalidWorld").replace("<world>", worldName).send(sender);
                return CheckResult.error("World '" + worldName + "' does not exist.");
            }
        } else {
            return CheckResult.error("World name is required.");
        }

        final String region = wgrpPlugin.getRegionAdapter().getProtectRegionName(regionName, world);
        if (region == null || region.equals("unknown-region")) {
            wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.invalidRegion").replace("<region>", regionName).send(sender);
            return CheckResult.error("Region '" + regionName + "' does not exist in world '" + world.getName() + "'.");
        }
        return CheckResult.success(new Check(regionName, world));
    }

    public record Check(String region, World finalWorld) {}

    private void getRgMap(@NotNull final CommandSender sender, final @NotNull Map<String, List<String>> rgMap, final String region, final String world) {
        if (rgMap.containsKey(world) && rgMap.get(world).contains(region)) {
            final List<String> newRegionList = new ArrayList<>(rgMap.get(world));
            newRegionList.remove(region);
            rgMap.put(world, newRegionList);
            wgrpPlugin.getConfigProvider().getConfig().setRegionProtectMap(rgMap);
            wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.remove").replace("<region>", region).send(sender);
        } else {
            wgrpPlugin.getMessageProvider().getMessages().get("messages.regionManagement.notContains").replace("<region>", region).send(sender);
        }
    }

    @SubCommand(
            name = "help",
            description = "for seen helping.")
    public void wgrpHelp(CommandSender sender) {
        final List<Component> messages = new ArrayList<>(this.wgrpPlugin.getMessageProvider().getMessages()
                .get("messages.usage.title")
                .replace("%command%", "/wgrp")
                .toComponentList(false));
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                final SubCommand sub = m.getAnnotation(SubCommand.class);
                final String tabArgs = String.join(" ", sub.tabArgs());
                messages.addAll(this.wgrpPlugin.getMessageProvider().getMessages().get("messages.usage.format")
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
            name = "version",
            permission = UtilPermissions.ADMIN_RIGHT,
            description = "seen what is version of plugin is running.")
    public void wgrpVersion(CommandSender sender) {
        final String pluginVersion = wgrpPlugin.getWgrpPaperBase().getDescription().getVersion();
        wgrpPlugin.messageToCommandSender(sender, String.format("<dark_gray>[<dark_red>WGRP<dark_gray>] <green>Current running version is <gold>%s", pluginVersion));
    }

    @SubCommand(
            name = "spy",
            permission = UtilPermissions.COMMAND_SPY_INSPECT_ADMIN,
            description = "spy for who interact with region.")
    public void wgrpSpy(@NotNull CommandSender sender) {
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
