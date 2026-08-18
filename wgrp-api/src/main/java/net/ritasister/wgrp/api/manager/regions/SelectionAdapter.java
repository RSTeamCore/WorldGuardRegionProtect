package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

public interface SelectionAdapter<P> {

    /**
     * Retrieves the name of the region based on the player's current selection in WorldEdit.
     *
     * @param player The player making the selection.
     * @return The name of the selected region as a {@code String}.
     * @since 0.7.6
     */
    String getProtectRegionNameBySelection(@NotNull P player);
}
