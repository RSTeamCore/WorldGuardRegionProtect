package net.ritasister.wgrp.api.manager.regions;

import org.jetbrains.annotations.NotNull;

public interface RegionWorldRegistry<W> {

    /**
     * Retrieves the protected region name for the specified region in the given world.
     *
     * <p>This method searches for a protected region in the specified world using the region's name.
     * If the region exists, its name is returned. If the region is not found,
     * an exception might be thrown or a default value may be returned depending on the implementation.</p>
     *
     * @param name the name of the region for which the protected region name is to be retrieved
     * @param world the world in which to search for the protected region
     * @return the name of the protected region if found, otherwise a default value or exception might be returned
     * @since 1.8.3.21
     */
    String getProtectRegionName(@NotNull String name, W world);
}
