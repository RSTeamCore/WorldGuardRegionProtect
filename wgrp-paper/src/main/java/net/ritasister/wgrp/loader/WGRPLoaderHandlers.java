package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.handler.TaskHandler;
import org.bukkit.plugin.PluginManager;

public final class WGRPLoaderHandlers implements LoadHandlers<WorldGuardRegionProtectPaperPlugin> {

    @Override
    public void loadHandler(WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        final Handler<Void> registerCommands = new CommandHandler(wgrpPlugin);
        registerCommands.handle();

        final Handler<PluginManager> registerListeners = new ListenerHandler(wgrpPlugin);
        registerListeners.handle(wgrpPlugin.getWgrpPaperBase().getServer().getPluginManager());

        final Handler<Void> registerTasks = new TaskHandler(wgrpPlugin);
        registerTasks.handle();
    }

}
