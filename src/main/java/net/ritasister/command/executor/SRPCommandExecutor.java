package net.ritasister.command.executor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.collect.Lists;

public abstract class SRPCommandExecutor implements CommandExecutor, TabExecutor 
{  
    private final transient String name;
    private Player p;
    
    protected SRPCommandExecutor(String name) 
    {
        this.name = name;
    }

	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
    {
        try{
			run(s,cmd,args);
		}catch(Exception e){
			e.printStackTrace();
		}
        return true;
    }

	protected abstract void run(CommandSender sender, Command cmd, String[] args) throws Exception;
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
	{
		return onTabComplete(sender, p, cmd, label, args);
	}
	
	protected abstract List<String> onTabComplete(CommandSender s, Player p, Command cmd, String label, String[] args);
}