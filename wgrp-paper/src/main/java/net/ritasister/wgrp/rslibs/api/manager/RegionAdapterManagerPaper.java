package net.ritasister.wgrp.rslibs.api.manager;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class RegionAdapterManagerPaper implements RegionAdapterManager<Location, Player, Region> {

    @Override
    public boolean isOwnerRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions, UUID uniqueId) {
        if (regions.get(location.getWorld().getName()) == null) {
            return false;
        }
        if (checkStandingRegion(location, regions)) {
            return this.getOwners(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean isOwnerRegion(@NotNull Location location, UUID uniqueId) {
        if (checkStandingRegion(location)) {
            return this.getOwners(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean isMemberRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions, UUID uniqueId) {
        if (regions.get(location.getWorld().getName()) == null) {
            return false;
        }
        if (checkStandingRegion(location, regions)) {
            return this.getMembers(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean isMemberRegion(@NotNull Location location, UUID uniqueId) {
        if (checkStandingRegion(location)) {
            return this.getMembers(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean checkStandingRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions) {
        for (String regionName : regions.get(location.getWorld().getName())) {
            return this.getApplicableRegions(location).getRegions().stream().anyMatch(region -> regionName.equalsIgnoreCase(region.getId()));
        }
        return regions.get(location.getWorld().getName()) == null;
    }

    @Override
    public boolean checkStandingRegion(@NotNull Location location) {
        return this.getApplicableRegions(location).size() != 0;
    }

    @Override
    public String getProtectRegionName(Location location) {
        return this.getApplicableRegions(location)
                .getRegions()
                .stream()
                .map(ProtectedRegion::getId)
                .collect(Collectors.joining());
    }

    @Override
    public String getProtectRegionNameBySelection(final Player player) throws NoSelectionException {
        final LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        Region selection = null;
        try {
            selection = localSession.getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException exception) {
            exception.fillInStackTrace();
        }
        return getProtectRegionNameByIntersection(selection);
    }

    @Override
    public String getProtectRegionNameByIntersection(final Region selection) throws NoSelectionException {
        if (selection instanceof CuboidRegion) {
            final BlockVector3 min = selection.getMinimumPoint();
            final BlockVector3 max = selection.getMaximumPoint();
            final RegionContainer rc = WorldGuard.getInstance().getPlatform().getRegionContainer();
            final RegionManager regions = rc.get(selection.getWorld());
            final ProtectedRegion __dummy__ = new ProtectedCuboidRegion("__dummy__", min, max);
            final ApplicableRegionSet applicableRegionSet = Objects.requireNonNull(regions).getApplicableRegions(__dummy__);
            return applicableRegionSet
                    .getRegions()
                    .stream()
                    .map(ProtectedRegion::getId)
                    .collect(Collectors.joining());
        }
        throw new NoSelectionException();
    }

    private @NotNull ApplicableRegionSet getApplicableRegions(final @NotNull Location location) {
        return Objects.requireNonNull(WorldGuard.getInstance()
                        .getPlatform()
                        .getRegionContainer()
                        .get(BukkitAdapter.adapt(location.getWorld())))
                .getApplicableRegions(BukkitAdapter.asBlockVector(location));
    }

    private boolean getOwners(final @NotNull Location location, UUID uniqueId) {
        return this.getApplicableRegions(location)
                .getRegions()
                .stream()
                .anyMatch(region -> region.getOwners().contains(uniqueId));
    }

    private boolean getMembers(final @NotNull Location location, UUID uniqueId) {
        return this.getApplicableRegions(location)
                .getRegions()
                .stream()
                .anyMatch(region -> region.getMembers().contains(uniqueId));
    }

}
