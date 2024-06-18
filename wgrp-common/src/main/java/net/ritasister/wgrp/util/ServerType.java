package net.ritasister.wgrp.util;

public enum ServerType {

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
