package net.ritasister.util.wg;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface Iwg 
{
   boolean wg(World world, Location loc, boolean bool);

   boolean checkIntersection(Player var1);

   boolean checkCIntersection(Player var1, String... var2);

   boolean checkPIntersection(Player var1, String... var2);

   boolean checkSIntersection(Player var1, String... var2);

   boolean checkUIntersection(Player var1, String... var2);

   boolean checkCPIntersection(Player var1, String... var2);
}
