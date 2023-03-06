package net.ritasister.wgrp.loader.plugin.abstracts;

import net.ritasister.wgrp.loader.plugin.interfaces.HookPlugin;
import net.ritasister.wgrp.loader.plugin.interfaces.LoadPlugin;

public interface LoadPluginImpl<V> extends LoadPlugin, HookPlugin<V> {

    @Override
    default void loadPlugin() {}

    @Override
    V hookPlugin();

}
