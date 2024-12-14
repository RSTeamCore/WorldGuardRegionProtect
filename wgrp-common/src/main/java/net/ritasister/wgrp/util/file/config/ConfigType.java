package net.ritasister.wgrp.util.file.config;

/**
 * Get to only need a type of file like config or lang file
 */
public enum ConfigType {

    LANG("lang"),
    CONFIG("config");

    private final String name;

    ConfigType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
