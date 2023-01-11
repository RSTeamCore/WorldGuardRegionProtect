package net.ritasister.wgrp.command.extend;

import lombok.SneakyThrows;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.UtilConfig;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CommandReload extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;
    private final UtilConfig utilConfig;

    public CommandReload(@NotNull final WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.getCommand(), wgRegionProtect);
        this.wgRegionProtect = wgRegionProtect;
        this.utilConfig = wgRegionProtect.getUtilConfig();
    }

    @SneakyThrows
    @SubCommand(
            name = "reload",
            permission = UtilPermissions.COMMAND_WGRP_RELOAD_CONFIGS,
            description = "")
    public void wgrpReload(@NotNull CommandSender sender) {
        long timeInitStart = System.currentTimeMillis();
        utilConfig.getConfig().reload();
        utilConfig.initConfig(wgRegionProtect, wgRegionProtect.getWGRPBukkitPlugin());

        long timeReload = (System.currentTimeMillis() - timeInitStart);
        utilConfig.getMessages().get("messages.Configs.configReloaded").replace(
                "<time>",
                String.valueOf(timeReload)
        ).send(sender);
    }

}
