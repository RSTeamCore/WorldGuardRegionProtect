package net.ritasister.srp;

import org.bukkit.*;
import org.bukkit.plugin.*;

import com.sk89q.worldguard.bukkit.*;

public class SRPLoadLibs 
{
	@SuppressWarnings("null")
	public static WorldGuardPlugin loadWorldGuard() 
	{
        final String s = "WorldGuard";
        final Plugin plg = ServerRegionProtect.instance.getServer().getPluginManager().getPlugin(s);
        if (plg != null && plg.isEnabled()) 
        {
            try{
                msgSuccess(s);
                return (WorldGuardPlugin)plg;
            }catch(NullPointerException | ClassCastException | NoClassDefFoundError ex){
                final Throwable t = null;
                final Throwable e = t;
                SRPLogger.err(e.getMessage());
            }
        }
        return null;
    }
	private static void msgSuccess(final String s) 
	{
        SRPLogger.info(ServerRegionProtect.colorize("Plugin: " + s + " loaded successfull!."));
    }
}