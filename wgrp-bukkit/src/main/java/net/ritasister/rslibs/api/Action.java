package net.ritasister.rslibs.api;

public enum Action {

    BREAK("break"),
    PLACE("place"),
    INTERACT("interact");

    private final String slot;

    Action(String slot) {
        this.slot = slot;
    }

    public String getSlot() {
        return slot;
    }
}
