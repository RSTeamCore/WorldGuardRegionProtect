package net.ritasister.wgrp.loader.plugin;

public abstract class AbstractLoadPlugin<V> implements LoadPluginImpl<V> {

    @Override
    public void loadPlugin() {}

    @Override
    public abstract V hookPlugin();

}
