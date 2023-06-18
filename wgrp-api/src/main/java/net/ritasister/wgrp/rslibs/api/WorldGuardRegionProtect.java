package net.ritasister.wgrp.rslibs.api;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface WorldGuardRegionProtect {

    boolean isDebug();

    void setDebug(boolean debug);

    default void messageToCommandSender(@NotNull CommandSender commandSender, String message) {
        var miniMessage = MiniMessage.miniMessage();
        Component parsed = miniMessage.deserialize(message);
        commandSender.sendMessage(parsed);
    }

}
