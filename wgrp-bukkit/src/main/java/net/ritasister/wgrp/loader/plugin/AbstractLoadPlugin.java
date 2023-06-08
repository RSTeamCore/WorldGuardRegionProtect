package net.ritasister.wgrp.loader.plugin;

public abstract class AbstractLoadPlugin<V> implements LoadPluginImpl<V> {

    @Override
    public abstract V hookPlugin();

}
