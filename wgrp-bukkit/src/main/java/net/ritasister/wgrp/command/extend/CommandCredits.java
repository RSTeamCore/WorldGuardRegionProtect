package net.ritasister.wgrp.command.extend;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.util.UtilCommandList;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandCredits extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandCredits(@NotNull final WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.getCommand(), wgRegionProtect);
        this.wgRegionProtect = wgRegionProtect;
    }

    @SubCommand(
            name = "about",
            aliases = {"credits", "authors"},
            description = "seeing info about authors.")
    public void wgrpCredits(@NotNull CommandSender sender) {
        wgRegionProtect.getRsApi().messageToCommandSender(sender, """
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

}
