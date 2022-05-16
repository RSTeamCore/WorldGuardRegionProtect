package net.ritasister.rslibs.api;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RSRegion {

    /**
     * Check access in standing region by player used region name from config.yml.
     *
     * @param loc return location of player.
     * @param list return list of regions from WorldGuard.
     *
     * @return checkStandingRegion return location of Player.
     */
    public boolean checkStandingRegion(@NotNull Location loc, List<String> list) {
        final World world = loc.getWorld();
        final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
        final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        assert regionManager != null;
        final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
        for (ProtectedRegion protectedRegion : applicableRegionSet) {
            for (String regionName : list) {
                if (protectedRegion.getId().equalsIgnoreCase(regionName)) {
                    return true;
                }
            }
        }return false;
    }

    /**
     * Check access in standing region by player without region name.
     *
     * @param loc return location of player.
     *
     * @return checkStandingRegion return location of Player.
     */
    public boolean checkStandingRegion(@NotNull Location loc) {
        final World world = loc.getWorld();
        final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
        final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        assert regionManager != null;
        final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
        return (applicableRegionSet.size() != 0);
    }

    /**
     * Getting region name for another method.
     *
     * @param loc return location of player.
     *
     * @return getProtectRegionName return location of Player.
     */
    public String getProtectRegion(@NotNull Location loc) {
        final World world = loc.getWorld();
        final RegionManager regionManager = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));
        final BlockVector3 locationBlockVector3 = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        assert regionManager != null;
        final ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(locationBlockVector3);
        for (ProtectedRegion protectedRegion : applicableRegionSet) {
            return protectedRegion.getId();
        }
        return null;
    }
}
