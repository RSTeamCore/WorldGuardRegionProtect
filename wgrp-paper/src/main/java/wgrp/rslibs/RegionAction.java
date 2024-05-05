package wgrp.rslibs;

import lombok.Getter;

/**
 *
 *
 */
@Getter
public enum RegionAction {

    BREAK("break"),
    PLACE("place"),
    INTERACT("interact");

    private final String action;

    RegionAction(String action) {
        this.action = action;
    }

}
