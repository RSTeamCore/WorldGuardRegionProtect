package net.ritasister.wgrp.rslibs.api;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.ritasister.wgrp.rslibs.api.interfaces.IRSRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class RSRegion implements IRSRegion {

    /**
     * Check access in standing region by player used region name from config.yml.
     *
     * @param location return location of object.
     * @param list return list of regions from WorldGuard.
     *
     * @return checkStandingRegion return location of object.
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location, List<String> list) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
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
     * @param location return location of object.
     *
     * @return checkStandingRegion return location of object.
     */
    @Override
    public boolean checkStandingRegion(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        return (applicableRegionSet.size() != 0);
    }

    @Override
    public boolean checkStandingRegion(World world, @NotNull Location location, List<String> list) {
        final ApplicableRegionSet set = this.getApplicableRegions(location);
        for (final ProtectedRegion rg : set) {
            for (String regionName : list) {
                if (rg.getId().equalsIgnoreCase(regionName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Getting region name for another method.
     *
     * @param location return location of object.
     *
     * @return getProtectRegionName return location of Object.
     */
    public String getProtectRegion(@NotNull Location location) {
        final ApplicableRegionSet applicableRegionSet = this.getApplicableRegions(location);
        for (ProtectedRegion protectedRegion : applicableRegionSet) {
            return protectedRegion.getId();
        }
        return null;
    }

    /**
     * Getting applicable region for set.
     *
     * @param location return location of Object.
     *
     * @return getApplicableRegions return location of Object.
     */
    private ApplicableRegionSet getApplicableRegions(final Location location) {
        return Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld())))
                .getApplicableRegions(BukkitAdapter.asBlockVector(location));
    }

    /*public void paginate(CommandSender sender, String[] args, boolean mine) {
        int page = 1;
        final List<List<String>> lists = mine
                ? ProtectedMine.getSplitedNames()
                : ProtectedRG.getSplitedNames();
        if (args.length == 2 && args[1].matches("^[0-9]{1,3}$") && Integer.parseInt(args[1]) <= lists.size()) {
            page = Integer.parseInt(args[1]);
        }
        sender.sendMessage("WGRP: " + " §3Page " + page + "/" + lists.size());
        for (final String item : lists.get(page - 1)) {
            sender.sendMessage("WGRP: " + " §2" + item);
        }
        sender.sendMessage("");
    }*/
}
