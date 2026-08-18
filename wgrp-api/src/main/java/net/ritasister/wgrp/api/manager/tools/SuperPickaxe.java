package net.ritasister.wgrp.api.manager.tools;

public interface SuperPickaxe<P> {

    /**
     * Checks if the Super Pickaxe tool is currently active for a given player.
     *
     * @param player the player to check
     * @return {@code true} if the Super Pickaxe is active; {@code false} otherwise
     */
    boolean isSuperPickaxeActive(P player);

}
