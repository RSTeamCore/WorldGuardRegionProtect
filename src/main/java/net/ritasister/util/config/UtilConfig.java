package net.ritasister.util.config;

import java.io.*;
import java.util.*;

import org.bukkit.World;
import org.bukkit.configuration.file.*;

import net.ritasister.wgrp.WorldGuardRegionProtect;

public final class UtilConfig
{
	private FileConfiguration config;

	public List<String> regionProtect;
	public List<String> regionProtectAllow;
	public List<String> regionProtectOnlyBreakAllow;
	public List<String> spawnEntityType;
	public List<String> interactType;
	public List<String> spyCommand;
	public List<String> CmdsWE;
	public List<String> cmdweC;
	public List<String> cmdweP;
	public List<String> cmdweS;
	public List<String> cmdweU;
	public List<String> cmdweCP;
	public List<String> cmdweB;
	public boolean regionMessageProtect;
	public boolean regionMessageProtectWe;
	public boolean spyCommandNotifyConsole;
	public boolean spyCommandNotifyAdmin;
	
	public UtilConfig() 
	{
		WorldGuardRegionProtect.instance.saveDefaultConfig();
		WorldGuardRegionProtect.instance.reloadConfig();
		this.config = WorldGuardRegionProtect.instance.getConfig();

		this.regionProtect=(List<String>)this.config.getList("worldguard_protect_region.region_protect",(List)new ArrayList());
		this.regionProtectAllow=(List<String>)this.config.getList("worldguard_protect_region.region_protect_allow",(List)new ArrayList());
		this.regionProtectOnlyBreakAllow=(List<String>)this.config.getList("worldguard_protect_region.region_protect_only_break_allow",(List)new ArrayList());
		this.spawnEntityType=(List<String>)this.config.getList("worldguard_protect_region.spawn_entity_type",(List)new ArrayList());
		this.interactType=(List<String>)this.config.getList("worldguard_protect_region.interact_type",(List)new ArrayList());

		this.CmdsWE=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_we",(List)new ArrayList());
		this.cmdweC=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_c",(List)new ArrayList());
		this.cmdweP=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_p",(List)new ArrayList());
		this.cmdweS=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_s",(List)new ArrayList());
		this.cmdweU=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_u",(List)new ArrayList());
		this.cmdweCP=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_cp",(List)new ArrayList());
		this.cmdweB=(List<String>)this.config.getList("worldguard_protect_region.no_protect_cmd.command_b",(List)new ArrayList());
		this.spyCommand=(List<String>)this.config.getList("worldguard_protect_region.spy_command.command_list",(List)new ArrayList());
		
		this.regionMessageProtect=this.config.getBoolean("worldguard_protect_region.protect_message");
		this.regionMessageProtectWe=this.config.getBoolean("worldguard_protect_region.protect_we_message");
		this.spyCommandNotifyConsole=this.config.getBoolean("worldguard_protect_region.notify.console");
		this.spyCommandNotifyAdmin=this.config.getBoolean("worldguard_protect_region.notify.admin");
	}
}