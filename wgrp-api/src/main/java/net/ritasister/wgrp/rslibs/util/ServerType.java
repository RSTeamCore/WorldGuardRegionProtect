package net.ritasister.wgrp.rslibs.util;

public enum ServerType {

    PAPER("PAPER"),
    SPIGOT("Spigot"),
    SPONGE("Sponge");

    private final String name;

    ServerType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
