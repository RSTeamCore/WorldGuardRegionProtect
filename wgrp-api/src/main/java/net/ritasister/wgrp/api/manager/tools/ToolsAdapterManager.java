package net.ritasister.wgrp.api.manager.tools;

/**
 * A region adapter manager for easy use of the WorldEdit API.
 *
 * @param <P> the type representing the player or actor interacting with the tools
 */
public interface ToolsAdapterManager<P> {

    /**
     * Checks if the Super Pickaxe tool is currently active for a given player.
     *
     * @param player the player to check
     * @return {@code true} if the Super Pickaxe is active; {@code false} otherwise
     */
    boolean isSuperPickaxeActive(P player);

}
