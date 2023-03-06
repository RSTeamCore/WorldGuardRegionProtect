package net.ritasister.wgrp.loader.interfaces;

public interface LoaderCommandsAndListeners<T> {

    void loadListeners(T clazz);

    void loadCommands(T clazz);

}
