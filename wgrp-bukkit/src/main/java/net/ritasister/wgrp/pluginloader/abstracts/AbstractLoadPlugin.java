package net.ritasister.wgrp.pluginloader.abstracts;

public abstract class AbstractLoadPlugin<V> implements LoadPluginImpl<V> {

    @Override
    public void loadPlugin() {}

    @Override
    public abstract V hookPlugin();

}
