package net.ritasister.util;

import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.*;

import net.ritasister.srp.SRPLogger;
import net.ritasister.srp.ServerRegionProtect;

import java.io.*;
import java.util.logging.*;

public class UtilLoadConfig
{
	private static ServerRegionProtect serverRegionProtect;

	public static File messagesf;
	public static FileConfiguration messages;

	public UtilLoadConfig(ServerRegionProtect instance)
	{
		serverRegionProtect=instance;
	}
	public static void LoadMSGConfig(ServerRegionProtect $m, boolean copy)
    {
		messagesf = new File($m.getDataFolder(), "messages.yml");
        if (!messagesf.exists()) 
        {
            if (copy) 
            {
                $m.saveResource("messages.yml", false);
                SRPLogger.LoadConfigMsgSuccess(messagesf);
            }else{
        	try
        	{
				messagesf.createNewFile();
			}catch(Exception ex){
				SRPLogger.LoadConfigMsgError(messagesf+ex.getMessage());
				ex.printStackTrace();
        	}
        }
	}
        try 
    	{
    		messages = YamlConfiguration.loadConfiguration(messagesf);
    	}catch(NullPointerException e){
    		SRPLogger.LoadConfigMsgError(messagesf+e.getMessage());
    		e.printStackTrace();
    	}
        SRPLogger.LoadConfigMsgSuccess(messagesf);
    }
	public static void SaveMsgConfig()
	{
		if(!messagesf.exists()) 
		{
			LoadMSGConfig(serverRegionProtect, true);
		}
		try 
		{
			messages = YamlConfiguration.loadConfiguration(messagesf);
		}catch(NullPointerException e){
			SRPLogger.err("Could not save " + e.getMessage());
			e.printStackTrace();
		}
		try
		{
			messages.save(messagesf);
		}catch(IOException e){
			SRPLogger.err("Could not load config. " + e.getMessage());
			e.printStackTrace();
		}
	}
}