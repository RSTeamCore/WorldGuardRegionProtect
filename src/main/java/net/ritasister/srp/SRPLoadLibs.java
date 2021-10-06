package net.ritasister.srp;

import org.bukkit.*;
import org.bukkit.plugin.*;

import com.sk89q.worldguard.bukkit.*;

import net.ritasister.rslibs.api.RSLogger;

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
                RSLogger.err(e.getMessage());
            }
        }
        return null;
    }
	private static void msgSuccess(final String s) 
	{
        RSLogger.info("&2Plugin: " + s + " loaded successfull!.");
    }
}