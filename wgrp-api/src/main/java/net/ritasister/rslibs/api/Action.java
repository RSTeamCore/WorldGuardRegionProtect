package net.ritasister.rslibs.api;

/**

 */
public enum Action {

    BREAK("break"),
    PLACE("place"),
    INTERACT("interact");

    private final String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
