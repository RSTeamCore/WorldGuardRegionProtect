package net.ritasister.wgrp.core;

import net.ritasister.wgrp.api.RegionAction;

/**
 * Check the type of action with regions.
 */
public enum RegionActionImpl implements RegionAction {

    BREAK("break"),
    PLACE("place"),
    INTERACT("interact");

    private final String action;

    RegionActionImpl(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

}
