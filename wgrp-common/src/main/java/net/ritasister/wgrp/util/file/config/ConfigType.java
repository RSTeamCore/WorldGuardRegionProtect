package net.ritasister.wgrp.util.file.config;

/**
 * Enum representing different types of configuration files.
 */
public enum ConfigType {

    /**
     * Language configuration file type.
     */
    LANG("lang"),

    /**
     * General configuration file type.
     */
    CONFIG("config");

    private final String name;

    ConfigType(final String name) {
        this.name = name;
    }

    /**
     * Returns the name of the configuration type.
     *
     * @return the name of the configuration type.
     */
    @Override
    public String toString() {
        return name;
    }
}
