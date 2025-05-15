package net.ritasister.wgrp.handler;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.command.extend.CommandWGRP;
import org.bukkit.command.CommandExecutor;

import java.util.List;
import java.util.stream.Collectors;

public class CommandHandler implements Handler<Void> {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public CommandHandler(WorldGuardRegionProtectPaperPlugin wgrpPaperPlugin) {
        this.wgrpPlugin = wgrpPaperPlugin;
    }

    @Override
    public void handle() {
        final List<CommandExecutor> allCommands = List.of(
                new CommandWGRP(wgrpPlugin)
        );

        allCommands.forEach(command ->
                this.wgrpPlugin.getLogger().info("Registered command: " + command.getClass().getSimpleName()));
        this.wgrpPlugin.getLogger().info(String.format("All commands registered successfully! List of available registered commands: %s",
                allCommands.stream()
                        .map(command -> command.getClass().getSimpleName())
                        .collect(Collectors.joining(", "))));
    }
}
