package net.ritasister.util.wg;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import net.ritasister.util.config.UtilConfig;
import net.ritasister.wgrp.WorldGuardRegionProtect;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class wg7 implements Iwg {

    public WorldGuardRegionProtect worldGuardRegionProtect;
    private final UtilConfig utilConfig;
    public WorldGuardPlugin wg;
    public WorldEditPlugin we;
    
    public wg7(final WorldGuardRegionProtect worldGuardRegionProtect, UtilConfig utilConfig) {
        this.worldGuardRegionProtect = worldGuardRegionProtect;
        this.wg = (WorldGuardPlugin)Bukkit.getPluginManager().getPlugin("WorldGuard");
        this.we = (WorldEditPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        this.utilConfig = utilConfig;
    }
    @Override
    public boolean wg(final World w, final Location l) {
        final ApplicableRegionSet set = this.getApplicableRegions(l);
        for (final ProtectedRegion rg : set) {
            for(final Object region : this.utilConfig.regionProtect) {
                if (rg.getId().equalsIgnoreCase(region.toString())) {
                    return true;
                }
            }
        }return false;
    }
    @Override
    public boolean checkIntersection(final Player player) {
        final LocalSession l = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
        Region sel = null;
        try{
            sel = l.getSelection(BukkitAdapter.adapt(player.getWorld()));
        }catch(IncompleteRegionException e){
            e.printStackTrace();
        }
        return this.checkIntersection(sel);
    }
    private boolean checkIntersection(final Region sel) {
        if (sel instanceof CuboidRegion) {
            final BlockVector3 min = sel.getMinimumPoint();
            final BlockVector3 max = sel.getMaximumPoint();
            final RegionContainer rc = WorldGuard.getInstance().getPlatform().getRegionContainer();
            final RegionManager regions = rc.get(sel.getWorld());
            final ProtectedRegion __dummy__ = new ProtectedCuboidRegion("__dummy__", min, max);
            final ApplicableRegionSet set = regions.getApplicableRegions(__dummy__);
            for (final ProtectedRegion rg : set) {
                for (final Object region : utilConfig.regionProtect) {
                    if (rg.getId().equalsIgnoreCase(region.toString())) {
                        return false;
                    }
                }
            }
            for (final ProtectedRegion rg : set) {
                for (final Object region : utilConfig.regionProtectOnlyBreakAllow) {
                    if (rg.getId().equalsIgnoreCase(region.toString())) {
                        return false;
                    }
                }
            }return true;
        }return false;
    }
    @Override
    public boolean checkCIntersection(final Player player, final String... args) {
        final Region sel = this.getCylSelection(player, args);
        return this.checkIntersection(sel);
    }
    @Override
    public boolean checkPIntersection(final Player player, final String... args) {
        final Region sel = this.getPyramidSelection(player, args);
        return this.checkIntersection(sel);
    }
    @Override
    public boolean checkSIntersection(final Player player, final String... args) {
        final Region sel = this.getSphereSelection(player, args);
        return this.checkIntersection(sel);
    }
    @Override
    public boolean checkUIntersection(final Player player, final String... args) {
        final Region sel = this.getUpSelection(player, args);
        return this.checkIntersection(sel);
    }
    @Override
    public boolean checkCPIntersection(final Player player, final String... args) {
        final Region sel = this.getPasteSelection(player, args);
        return this.checkIntersection(sel);
    }
    private CuboidRegion getCylSelection(final Player player, final String... args) {
        int x = 1;
        int y = 1;
        int z = 0;
        try{
            if (args[2].contains(",")) 
            {
                x = Integer.parseInt(args[2].split(",")[0]);
                z = Integer.parseInt(args[2].split(",")[1]);
            }else{
                x = Integer.parseInt(args[2]);
            }
            y = Integer.parseInt(args[3]);
        }catch(Exception ignored){}
        final Location loc1 = player.getLocation().subtract(x, y, z);
        final Location loc2 = player.getLocation().add(x, y, z);
        return new CuboidRegion(BukkitAdapter.adapt(player.getWorld()), BukkitAdapter.asVector(loc1).toBlockPoint(), BukkitAdapter.asVector(loc2).toBlockPoint());
    }
    private CuboidRegion getPyramidSelection(final Player player, final String... args) {
        if (args.length < 3) 
        {
            return null;
        }
        int i = 1;
        try{
            i = Integer.parseInt(args[2]);
        }catch(Exception ignored){}
        final Location loc1 = player.getLocation().subtract(i, i, i);
        final Location loc2 = player.getLocation().add(i, i, i);
        return new CuboidRegion(BukkitAdapter.adapt(player.getWorld()), BukkitAdapter.asVector(loc1).toBlockPoint(), BukkitAdapter.asVector(loc2).toBlockPoint());
    }
    private CuboidRegion getSphereSelection(final Player player, final String... args) {
        if (args.length < 3) 
        {
            return null;
        }
        final String[] cr = args[2].split(",");
        int y2;
        int z2;
        final int x2 = z2 = (y2 = Integer.parseInt(cr[0]));
        try{
            y2 = Integer.parseInt(cr[1]);
            z2 = Integer.parseInt(cr[2]);
        }catch(Exception ignored){}
        final Location loc1 = player.getLocation().subtract(x2, y2, z2);
        final Location loc2 = player.getLocation().add(x2, y2, z2);
        return new CuboidRegion(BukkitAdapter.adapt(player.getWorld()), BukkitAdapter.asBlockVector(loc1), BukkitAdapter.asBlockVector(loc2));
    }
    private CuboidRegion getUpSelection(final Player player, final String... args) {
        try{
            final int v = Integer.parseInt(args[1]);
            final Location loc1 = player.getLocation().add(0.0, v, 0.0);
            final Location loc2 = player.getLocation().add(0.0, v, 0.0);
            return new CuboidRegion(BukkitAdapter.adapt(player.getWorld()), BukkitAdapter.asBlockVector(loc1), BukkitAdapter.asBlockVector(loc2));
        }catch(Exception ex){
            return null;
        }
    }
    private CuboidRegion getPasteSelection(final Player player, final String... args) {
        try{
            final LocalSession session = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(player));
            final ClipboardHolder holder = session.getClipboard();
            final Clipboard clipboard = holder.getClipboard();
            final BlockVector3 to = session.getPlacementPosition(BukkitAdapter.adapt(player));
            final BlockVector3 min = to.add(clipboard.getRegion().getMinimumPoint().subtract(clipboard.getOrigin()));
            final BlockVector3 max = to.add(clipboard.getRegion().getMaximumPoint().subtract(clipboard.getOrigin()));
            return new CuboidRegion(BukkitAdapter.adapt(player.getWorld()), min, max);
        }catch (Exception e){
            return null;
        }
    }
    private ApplicableRegionSet getApplicableRegions(final Location l) {
        return WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(l.getWorld())).getApplicableRegions(BukkitAdapter.asBlockVector(l));
    }
}