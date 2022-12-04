package net.ritasister.wgrp.rslibs.util.wg;

import com.google.inject.assistedinject.Assisted;
import com.sk89q.worldedit.IncompleteRegionException;
import org.bukkit.entity.Player;

public interface Iwg {
   @Assisted boolean checkIntersection(Player player) throws IncompleteRegionException;
   @Assisted boolean checkCIntersection(Player player, String... args) throws IncompleteRegionException;
   @Assisted boolean checkPIntersection(Player player, String... args) throws IncompleteRegionException;
   @Assisted boolean checkSIntersection(Player player, String... args) throws IncompleteRegionException;
   @Assisted boolean checkUIntersection(Player player, String... args) throws IncompleteRegionException;
   @Assisted boolean checkCPIntersection(Player player, String... args) throws IncompleteRegionException;
}
