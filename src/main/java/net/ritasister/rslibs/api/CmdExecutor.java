package net.ritasister.rslibs.api;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public abstract class CmdExecutor implements CommandExecutor, TabExecutor 
{
    private final transient String name;
    private Player p;
    
    protected CmdExecutor(String name) {this.name = name;}
    /*
	 * Constructor for command.
	 * 
	 * @return onCommand.
	 */
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
    {
        try{
			run(s,cmd,args);
		}catch(Exception e){
			e.printStackTrace();
		}
        return true;
    }
	/*
	 * Constructor of TAB for command.
	 * 
	 * @return onTabComplete.
	 */
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args)
	{
		return onTabComplete(sender, p, cmd, label, args);
	}
	protected abstract void run(CommandSender sender, Command cmd, String[] args) throws Exception;
	
	protected abstract List<String> onTabComplete(CommandSender s, Player p, Command cmd, String label, String[] args);
}