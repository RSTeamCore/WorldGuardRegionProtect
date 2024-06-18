package net.ritasister.wgrp.util;

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
