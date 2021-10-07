package net.ritasister.wgrp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.*;
import java.util.stream.*;
import javax.annotation.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.inventory.*;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.*;

import com.sk89q.worldguard.*;
import com.sk89q.worldguard.bukkit.*;
import com.sk89q.worldguard.protection.*;
import com.sk89q.worldguard.protection.regions.*;

import net.ritasister.register.RegisterCommand;
import net.ritasister.register.RegisterListener;
import net.ritasister.register.RegisterCommand;
import net.ritasister.register.RegisterListener;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.rslibs.utils.Metrics;
import net.ritasister.rslibs.utils.UpdateChecker;
import net.ritasister.util.UtilCommandList;
import net.ritasister.util.UtilConfigManager;
import net.ritasister.util.UtilLoadConfig;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;

public class WorldGuardRegionProtect extends JavaPlugin
{
	public static WorldGuardRegionProtect instance;
	
	public static UtilConfig utilConfig;
	public static UtilConfigMessage utilConfigMessage;
	public static UtilLoadConfig utilLoadConfig = new UtilLoadConfig(instance);
	
	private UtilConfigManager utilConfigManager;	
	private UtilCommandList utilCommandList;
	
	private LoadLibs loadLibs;
	private RegisterListener registerEvents;
	private RegisterCommand registerCommands;
	
	private final PluginManager pluginManager = getServer().getPluginManager();
	private final String pluginVersion = getDescription().getVersion();

	public WorldGuardRegionProtect() 
	{
		WorldGuardRegionProtect.instance = this;
	}
	
	@Override
	public void onEnable()
	{
		this.checkVersion();
		this.checkUpdate();
		this.loadMetrics();
		this.loadLibs.loadWorldGuard();
		this.utilConfigManager.loadConfig();
		this.registerCommands.RegisterCommands(utilCommandList);
		this.registerEvents.RegisterEvents(pluginManager);
		RSLogger.info("&2created by &8[&5RitaSister&8]");
	}
	private void checkUpdate()
	{
		new UpdateChecker(this, 81333).getVersion(version -> 
		{
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) 
            {	
            	RSLogger.info("&6==============================================");
                RSLogger.info("&2Current version: &b<pl_ver>".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&2This is latest version plugin.".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&6==============================================");
            }else{
            	RSLogger.info("&6==============================================");
                RSLogger.info("&eThere is a new version update available.");
                RSLogger.info("&cCurrent version: &4<pl_ver>".replace("<pl_ver>", pluginVersion));
                RSLogger.info("&3New version: &b<new_pl_ver>".replace("<new_pl_ver>", version));
                RSLogger.info("&ePlease download new version here:");
                RSLogger.info("&ehttps://www.spigotmc.org/resources/serverregionprotect-1-13-1-17.81321/");
                RSLogger.info("&6==============================================");
            }
        });
	}
	private void checkVersion() 
	{
		final String javaVersion = System.getProperty("java.version");
        final int dotIndex = javaVersion.indexOf('.');
        final int endIndex = dotIndex == -1 ? javaVersion.length() : dotIndex;
        final String version = javaVersion.substring(0, endIndex);
        final int javaVersionNum;
        try{
            javaVersionNum = Integer.parseInt(version);
        }catch(final NumberFormatException e){
            RSLogger.warn("Failed to determine Java version; Could not parse {}".replace("{}", version) + e);
            RSLogger.warn(String.valueOf(javaVersion));
            return;
        }
		String serverVersion;
        try{
            serverVersion = this.instance.getServer().getClass().getPackage().getName().split("\\.")[3];
        }catch(ArrayIndexOutOfBoundsException whatVersionAreYouUsingException){
            return;
        }
        RSLogger.info("&6You are running is &ejava &6version: &e<javaVersion>".replace("<javaVersion>", String.valueOf(javaVersionNum)));
		RSLogger.info("&6Your &eserver &6is running version: &e<serverVersion>".replace("<serverVersion>", String.valueOf(serverVersion)));
	}
	private void loadMetrics()
	{
		int pluginId = 10853;
        Metrics metrics = new Metrics(this, pluginId);
	}
}