package net.ritasister.wgrp.api.regions;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Getting different types of interactions with the region
 */
public interface RegionAction {

    RegionAction.@NonNull Type getType();

    enum Type {
        BREAK("break"),
        PLACE("place"),
        INTERACT("interact");

        private final String action;

        Type(String action) {
            this.action = action;
        }

        public String getAction() {
            return this.action;
        }
    }

}
