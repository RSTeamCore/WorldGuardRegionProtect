package net.ritasister.wgrp.rslibs.util.wg;

import org.bukkit.entity.Player;

public interface Iwg {
   boolean checkIntersection(Player var1);
   boolean checkCIntersection(Player var1, String... var2);
   boolean checkPIntersection(Player var1, String... var2);
   boolean checkSIntersection(Player var1, String... var2);
   boolean checkUIntersection(Player var1, String... var2);
   boolean checkCPIntersection(Player var1, String... var2);
}
