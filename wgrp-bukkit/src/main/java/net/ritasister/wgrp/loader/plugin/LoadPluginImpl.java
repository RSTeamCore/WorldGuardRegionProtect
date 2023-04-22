package net.ritasister.wgrp.loader.plugin;

public interface LoadPluginImpl<V> extends LoadPlugin, HookPlugin<V> {

    @Override
    default void loadPlugin() {}

    @Override
    V hookPlugin();

}
