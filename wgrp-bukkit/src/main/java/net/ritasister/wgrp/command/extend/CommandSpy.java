package net.ritasister.wgrp.command.extend;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class CommandSpy extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;

    public CommandSpy(@NotNull final WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.getCommand(), wgRegionProtect);
        this.wgRegionProtect = wgRegionProtect;
    }

    @SubCommand(
            name = "spy",
            permission = UtilPermissions.COMMAND_SPY_INSPECT_ADMIN,
            description = "spy for who interact with region.")
    public void wgrpSpy(@NotNull CommandSender sender, String[] args) {
        @NotNull UUID uniqueId = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getUniqueId();
        if (wgRegionProtect.getSpyLog().contains(uniqueId)) {
            wgRegionProtect.getSpyLog().remove(uniqueId);
            sender.sendMessage("You are disable spy logging!");
        } else {
            wgRegionProtect.getSpyLog().add(uniqueId);
            sender.sendMessage("You are enable spy logging!");
        }
    }

}
