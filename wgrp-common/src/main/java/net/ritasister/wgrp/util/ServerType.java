package net.ritasister.wgrp.util;

/**
 * Get to only need a type of what platform is used.
 */
public enum ServerType {

    SPIGOT("Spigot"),
    PAPER("Paper"),
    FOLIA("Folia"),
    UNKNOWN("Unknown");

    private final String name;

    ServerType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
