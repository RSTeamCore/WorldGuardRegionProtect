package net.ritasister.util.config;

import java.util.*;

import org.bukkit.configuration.file.*;

import net.ritasister.wgrp.WorldGuardRegionProtect;

public final class UtilConfig
{
	public List<String> regionProtect;
	public List<String> regionProtectAllow;
	public List<String> regionProtectOnlyBreakAllow;
	public List<String> spawnEntityType;
	public List<String> interactType;
	public List<String> spyCommand;
	public List<String> cmdWe;
	public List<String> cmdWeC;
	public List<String> cmdWeP;
	public List<String> cmdWeS;
	public List<String> cmdWeU;
	public List<String> cmdWeCP;
	public List<String> cmdWeB;
	public boolean regionMessageProtect;
	public boolean regionMessageProtectWe;
	public boolean spyCommandNotifyConsole;
	public boolean spyCommandNotifyAdmin;
	
	public UtilConfig()
	{
		WorldGuardRegionProtect.instance.saveDefaultConfig();
		WorldGuardRegionProtect.instance.reloadConfig();
		FileConfiguration config = WorldGuardRegionProtect.instance.getConfig();

		this.regionProtect= (List<String>) config.getList("worldguard_protect_region.region_protect",(List)new ArrayList());
		this.regionProtectAllow=(List<String>) config.getList("worldguard_protect_region.region_protect_allow",(List)new ArrayList());
		this.regionProtectOnlyBreakAllow=(List<String>) config.getList("worldguard_protect_region.region_protect_only_break_allow",(List)new ArrayList());
		this.spawnEntityType=(List<String>) config.getList("worldguard_protect_region.spawn_entity_type",(List)new ArrayList());
		this.interactType=(List<String>) config.getList("worldguard_protect_region.interact_type",(List)new ArrayList());

		this.cmdWe=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_we",(List)new ArrayList());
		this.cmdWeC=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_c",(List)new ArrayList());
		this.cmdWeP=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_p",(List)new ArrayList());
		this.cmdWeS=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_s",(List)new ArrayList());
		this.cmdWeU=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_u",(List)new ArrayList());
		this.cmdWeCP=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_cp",(List)new ArrayList());
		this.cmdWeB=(List<String>) config.getList("worldguard_protect_region.no_protect_cmd.command_b",(List)new ArrayList());
		this.spyCommand=(List<String>) config.getList("worldguard_protect_region.spy_command.command_list",(List)new ArrayList());
		
		this.regionMessageProtect= config.getBoolean("worldguard_protect_region.protect_message");
		this.regionMessageProtectWe= config.getBoolean("worldguard_protect_region.protect_we_message");
		this.spyCommandNotifyConsole= config.getBoolean("worldguard_protect_region.spy_command.notify.console");
		this.spyCommandNotifyAdmin= config.getBoolean("worldguard_protect_region.spy_command.notify.admin");
	}
}