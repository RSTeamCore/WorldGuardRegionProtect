package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.handler.Handler;
import net.ritasister.wgrp.api.handler.LoadHandlers;
import net.ritasister.wgrp.handler.CommandHandler;
import net.ritasister.wgrp.handler.ListenerHandler;
import net.ritasister.wgrp.handler.TaskHandler;

import java.util.List;

public final class WGRPLoaderHandlers implements LoadHandlers<WorldGuardRegionProtectPaperPlugin> {

    private final List<Handler<?>> handlers;

    public WGRPLoaderHandlers(List<Handler<?>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void loadHandler(WorldGuardRegionProtectPaperPlugin plugin) {
        handlers.forEach(Handler::handle);
    }
}
