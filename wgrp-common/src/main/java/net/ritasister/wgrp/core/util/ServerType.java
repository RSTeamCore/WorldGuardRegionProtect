package net.ritasister.wgrp.core.util;

public enum ServerType {

    PAPER("Paper"),
    SPIGOT("Spigot");

    private final String name;

    ServerType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
