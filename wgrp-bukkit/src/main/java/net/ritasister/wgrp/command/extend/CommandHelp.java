package net.ritasister.wgrp.command.extend;

import net.kyori.adventure.text.Component;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.command.AbstractCommand;
import net.ritasister.wgrp.rslibs.annotation.SubCommand;
import net.ritasister.wgrp.util.UtilCommandList;
import net.ritasister.wgrp.util.UtilConfig;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CommandHelp extends AbstractCommand {

    private final WorldGuardRegionProtect wgRegionProtect;
    private final UtilConfig utilConfig;

    public CommandHelp(@NotNull final WorldGuardRegionProtect wgRegionProtect) {
        super(UtilCommandList.WGRP.getCommand(), wgRegionProtect);
        this.wgRegionProtect = wgRegionProtect;
        this.utilConfig = wgRegionProtect.getUtilConfig();
    }

    @SubCommand(
            name = "help",
            description = "for seen helping.")
    public void wgrpHelp(CommandSender sender) {
        List<Component> messages = new ArrayList<>(
                wgRegionProtect
                        .getUtilConfig()
                        .getMessages()
                        .get("messages.usage.title")
                        .replace("%command%", "/wgrp")
                        .toComponentList(false));
        for (Method m : this.getClass().getDeclaredMethods()) {
            if (m.isAnnotationPresent(SubCommand.class)) {
                SubCommand sub = m.getAnnotation(SubCommand.class);
                String tabArgs = String.join(" ", sub.tabArgs());
                messages.addAll(utilConfig.getMessages().get("messages.usage.format")
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

}
