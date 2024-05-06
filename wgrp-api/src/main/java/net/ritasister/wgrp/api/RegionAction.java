package net.ritasister.wgrp.api;

/**
 * Check the type of action with regions.
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
