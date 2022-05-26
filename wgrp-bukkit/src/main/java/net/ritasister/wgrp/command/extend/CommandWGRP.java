package net.ritasister.wgrp.command.extend;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.command.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.config.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.UUID;

public class CommandWGRP extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandWGRP(@NotNull WorldGuardRegionProtect wgRegionProtect) {
        super("wgregionprotect", wgRegionProtect);
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