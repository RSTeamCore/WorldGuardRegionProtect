package net.rsteamcore.wgrp.rslibs.api;

/**
 *
 *
 */
public enum RegionAction {

    BREAK("break"),
    PLACE("place"),
    INTERACT("interact");

    private final String action;

    RegionAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
