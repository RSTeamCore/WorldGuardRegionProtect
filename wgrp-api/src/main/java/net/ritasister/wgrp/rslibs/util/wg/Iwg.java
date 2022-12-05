package net.ritasister.wgrp.rslibs.util.wg;

import com.sk89q.worldedit.IncompleteRegionException;
import org.bukkit.entity.Player;

public interface Iwg {
   boolean checkIntersection(Player player) throws IncompleteRegionException;
   boolean checkCIntersection(Player player, String... args) throws IncompleteRegionException;
   boolean checkPIntersection(Player player, String... args) throws IncompleteRegionException;
   boolean checkSIntersection(Player player, String... args) throws IncompleteRegionException;
   boolean checkUIntersection(Player player, String... args) throws IncompleteRegionException;
   boolean checkCPIntersection(Player player, String... args) throws IncompleteRegionException;
}
