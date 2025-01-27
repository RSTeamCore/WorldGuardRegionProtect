package net.ritasister.wgrp.rslibs.manager.region;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.ritasister.wgrp.api.manager.regions.RegionAdapterManager;
import net.ritasister.wgrp.rslibs.exceptions.NoSelectionException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RegionAdapterManagerPaper implements RegionAdapterManager<Location, Player> {

    @Override
    public boolean isOwnerRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions, @NotNull UUID uniqueId) {
        if (regions.get(location.getWorld().getName()) == null) {
            return false;
        }
        if (checkStandingRegion(location, regions)) {
            return this.getOwners(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean isOwnerRegion(@NotNull Location location, @NotNull UUID uniqueId) {
        if (checkStandingRegion(location)) {
            return this.getOwners(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean isMemberRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions, @NotNull UUID uniqueId) {
        if (regions.get(location.getWorld().getName()) == null) {
            return false;
        }
        if (checkStandingRegion(location, regions)) {
            return this.getMembers(location, uniqueId);
        }
        return false;
    }

    @Override
    public boolean isMemberRegion(@NotNull Location location, @NotNull UUID uniqueId) {
        if (checkStandingRegion(location)) {
            return this.getMembers(location, uniqueId);
        }
        return false;
    }

    @Override
    public int getPriorityRegion(@NotNull Location location) {
        if (checkStandingRegion(location)) {
            return this.getRegionPriority(location);
        }
        return 0;
    }

    @Override
    public boolean checkStandingRegion(@NotNull Location location, @NotNull Map<String, List<String>> regions) {
        if (regions.get(location.getWorld().getName()) == null) {
            return false;
        }
        return regions.get(location.getWorld().getName())
                .stream()
                .anyMatch(regionName ->
                        this.getApplicableRegionsSet(location)
                                .getRegions()
                                .stream()
                                .anyMatch(region -> regionName.equalsIgnoreCase(region.getId())));
    }

    @Override
    public boolean checkStandingRegion(@NotNull Location location) {
        return this.getApplicableRegionsSet(location).getRegions().isEmpty();
    }

    @Override
    public String getProtectRegionName(@NotNull Location location) {
        return this.getApplicableRegionsSet(location)
                .getRegions()
                .stream()
                .map(ProtectedRegion::getId)
                .collect(Collectors.joining());
    }

    @Override
    public String getProtectRegionNameBySelection(final @NotNull Player player) {
        final LocalSession localSession = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        final Region selection;
        try {
            selection = localSession.getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException e) {
            throw new NoSelectionException();
        }
        return this.getApplicableRegionsSet(selection)
                .getRegions()
                .stream()
                .map(ProtectedRegion::getId)
                .collect(Collectors.joining(", "));
    }

    private int getRegionPriority(final @NotNull Location location) {
        return this.getApplicableRegionsSet(location)
                .getRegions()
                .stream()
                .mapToInt(ProtectedRegion::getPriority).max().orElse(0);
    }

    private boolean getOwners(final @NotNull Location location, UUID uniqueId) {
        return this.getApplicableRegionsSet(location)
                .getRegions()
                .stream()
                .anyMatch(region -> region.getOwners().contains(uniqueId));
    }

    private boolean getMembers(final @NotNull Location location, UUID uniqueId) {
        return this.getApplicableRegionsSet(location)
                .getRegions()
                .stream()
                .anyMatch(region -> region.getMembers().contains(uniqueId));
    }

    private @NotNull ApplicableRegionSet getApplicableRegionsSet(final @NotNull Location location) {
        return Optional.ofNullable(WorldGuard.getInstance()
                        .getPlatform()
                        .getRegionContainer()
                        .get(BukkitAdapter.adapt(location.getWorld()))).map(regionManager ->
                        regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(location))).orElseThrow(() ->
                        new IllegalStateException("RegionManager is not available for the world: " + location.getWorld().getName()));
    }

    private @NotNull ApplicableRegionSet getApplicableRegionsSet(final @NotNull Region selection) {
        return Optional.ofNullable(WorldGuard.getInstance()
                        .getPlatform()
                        .getRegionContainer()
                        .get(selection.getWorld()))
                .map(regionManager -> 
                        regionManager.getApplicableRegions(new ProtectedCuboidRegion("__dummy__", 
                                selection.getMinimumPoint(), 
                                selection.getMaximumPoint()))).orElseThrow(() ->
                        new IllegalStateException("RegionManager is not available for the world: " 
                                + (selection.getWorld() != null ? selection.getWorld().getName() : "unknown world")));
    }
    
}
