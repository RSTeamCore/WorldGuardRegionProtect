package net.ritasister.util;

import org.bukkit.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.event.*;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;

import java.io.*;
import java.util.logging.*;

public class UtilLoadConfig
{
	private static WorldGuardRegionProtect worldGuardRegionProtect;

	public static File messagesf;
	public static FileConfiguration messages;

	public UtilLoadConfig(WorldGuardRegionProtect instance)
	{
		worldGuardRegionProtect=instance;
	}
	public static void LoadMSGConfig(WorldGuardRegionProtect $m, boolean copy)
    {
		messagesf = new File($m.getDataFolder(), "messages.yml");
        if (!messagesf.exists()) 
        {
            if (copy) 
            {
                $m.saveResource("messages.yml", false);
                RSLogger.LoadConfigMsgSuccess(messagesf);
            }else{
        	try
        	{
				messagesf.createNewFile();
			}catch(Exception ex){
				RSLogger.LoadConfigMsgError(messagesf+ex.getMessage());
				ex.printStackTrace();
        	}
        }
	}
        try 
    	{
    		messages = YamlConfiguration.loadConfiguration(messagesf);
    	}catch(NullPointerException e){
    		RSLogger.LoadConfigMsgError(messagesf+e.getMessage());
    		e.printStackTrace();
    	}
        RSLogger.LoadConfigMsgSuccess(messagesf);
    }
	public static void SaveMsgConfig()
	{
		if(!messagesf.exists()) 
		{
			LoadMSGConfig(worldGuardRegionProtect, true);
		}
		try 
		{
			messages = YamlConfiguration.loadConfiguration(messagesf);
		}catch(NullPointerException e){
			RSLogger.err("Could not save " + e.getMessage());
			e.printStackTrace();
		}
		try
		{
			messages.save(messagesf);
		}catch(IOException e){
			RSLogger.err("Could not load config. " + e.getMessage());
			e.printStackTrace();
		}
	}
}