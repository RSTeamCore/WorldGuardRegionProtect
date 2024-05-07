package net.ritasister.wgrp.api.regions;

/**
 * Getting different types of interactions with the region
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
        return this.action;
    }

}
