package net.ritasister.wgrp.loader.plugin.abstracts;

public abstract class AbstractLoadPlugin<V> implements LoadPluginImpl<V> {

    @Override
    public void loadPlugin() {}

    @Override
    public abstract V hookPlugin();

}
