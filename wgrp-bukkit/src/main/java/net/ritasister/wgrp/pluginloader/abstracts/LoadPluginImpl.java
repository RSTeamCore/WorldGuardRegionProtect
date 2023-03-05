package net.ritasister.wgrp.pluginloader.abstracts;

import net.ritasister.wgrp.pluginloader.interfaces.HookPlugin;
import net.ritasister.wgrp.pluginloader.interfaces.LoadPlugin;

public interface LoadPluginImpl<V> extends LoadPlugin, HookPlugin<V> {

    @Override
    default void loadPlugin() {}

    @Override
    V hookPlugin();

}
