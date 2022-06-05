package net.ritasister.wgrp.rslibs.api;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.ritasister.wgrp.rslibs.api.interfaces.IRSRegion;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RSRegion implements IRSRegion {

    /**
     * Check access in standing region by player used region name from config.yml.
     *
     * @param location return location of object.
     * @param regions maps of regions from WorldGuard.
     *
     * @return location of object.
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location, @NotNull HashMap<String, List<String>> regions) {
        if (regions.get(location.getWorld().getName()) == null) return false;
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        for (String regionName : regions.get(location.getWorld().getName())) {
            if (applicableRegionSet
                    .getRegions()
                    .stream()
                    .anyMatch(region -> regionName.equalsIgnoreCase(region.getId()))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check access in standing region by player without region name.
     *
     * @param location location of object.
     *
     * @return location of object.
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        return (applicableRegionSet.size() != 0);
    }

    /**
     * Get region name.
     *
     * @param location location of object.
     *
     * @return location of Object.
     */
    public String getProtectRegionName(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        return applicableRegionSet
                .getRegions()
                .stream()
                .map(ProtectedRegion::getId)
                .collect(Collectors.joining());
    }

    /**
     * Getting applicable region for set.
     *
     * @param location location of Object.
     *
     * @return location of Object.
     */
    private @NotNull ApplicableRegionSet getApplicableRegions(final @NotNull Location location) {
        return Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld())))
                .getApplicableRegions(BukkitAdapter.asBlockVector(location));
    }
}