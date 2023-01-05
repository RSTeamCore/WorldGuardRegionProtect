package net.ritasister.wgrp.rslibs.util.wg;

import org.bukkit.entity.Player;

public interface wg {

    boolean checkIntersection(Player player);

    boolean checkCIntersection(Player player, String... args);

    boolean checkPIntersection(Player player, String... args);

    boolean checkSIntersection(Player player, String... args);

    boolean checkUIntersection(Player player, String... args);

    boolean checkCPIntersection(Player player, String... args);

}
