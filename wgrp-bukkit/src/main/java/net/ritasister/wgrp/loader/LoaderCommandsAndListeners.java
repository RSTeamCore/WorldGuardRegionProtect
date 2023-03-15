package net.ritasister.wgrp.loader;

public interface LoaderCommandsAndListeners<T> {

    void loadListeners(T clazz);

    void loadCommands(T clazz);

}
