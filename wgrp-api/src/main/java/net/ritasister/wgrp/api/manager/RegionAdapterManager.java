package net.ritasister.wgrp.api.manager;

import net.ritasister.wgrp.api.manager.regions.RegionAccessManager;
import net.ritasister.wgrp.api.manager.regions.RegionLookup;
import net.ritasister.wgrp.api.manager.regions.RegionWorldRegistry;
import net.ritasister.wgrp.api.manager.regions.SelectionAdapter;

/**
 * A composite manager interface that unites specialized region-based operations.
 * * <p>This interface serves as a centralized contract, combining geometry lookups,
 * access rights validation, world registries, and selection adapter functionalities.
 * It is designed to maintain backwards compatibility while adhering to the
 * Interface Segregation Principle (ISP).</p>
 *
 * @param <L> The platform-specific Location object type (e.g., Bukkit Location or WorldGuard Vector).
 * @param <P> The platform-specific Player object type (e.g., Bukkit Player).
 * @param <W> The platform-specific World object type (e.g., Bukkit World).
 * * @see RegionLookup
 * @see RegionAccessManager
 * @see RegionWorldRegistry
 * @see SelectionAdapter
 * @since 1.11.2.25
 */
public interface RegionAdapterManager<L, P, W> extends
        RegionLookup<L>,
        RegionAccessManager<L>,
        RegionWorldRegistry<W>,
        SelectionAdapter<P> {
}
