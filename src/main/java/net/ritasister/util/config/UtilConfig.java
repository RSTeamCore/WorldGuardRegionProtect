package net.ritasister.util.config;

import java.util.*;

import org.bukkit.configuration.ConfigurationSection;

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
	public String spyCommandNotifyAdminPlaySound;
	public boolean spyCommandNotifyAdminPlaySoundEnable;

	@SuppressWarnings("unchecked")
	public UtilConfig()
	{
		WorldGuardRegionProtect.instance.saveDefaultConfig();
		WorldGuardRegionProtect.instance.reloadConfig();

		this.regionProtect = (List<String>) getPatch().getList("region_protect", new ArrayList<String>());
		this.regionProtectAllow = (List<String>) getPatch().getList("region_protect_allow", new ArrayList<String>());
		this.regionProtectOnlyBreakAllow = (List<String>) getPatch().getList(".region_protect_only_break_allow", new ArrayList<String>());
		this.spawnEntityType = (List<String>) getPatch().getList("spawn_entity_type", new ArrayList<String>());
		this.interactType = (List<String>) getPatch().getList("interact_type", new ArrayList<String>());

		this.cmdWe = (List<String>) getPatch().getList("no_protect_cmd.command_we", new ArrayList<String>());
		this.cmdWeC = (List<String>) getPatch().getList("no_protect_cmd.command_c", new ArrayList<String>());
		this.cmdWeP = (List<String>) getPatch().getList("no_protect_cmd.command_p", new ArrayList<String>());
		this.cmdWeS = (List<String>) getPatch().getList("no_protect_cmd.command_s", new ArrayList<String>());
		this.cmdWeU = (List<String>) getPatch().getList("no_protect_cmd.command_u", new ArrayList<String>());
		this.cmdWeCP = (List<String>) getPatch().getList("no_protect_cmd.command_cp", new ArrayList<String>());
		this.cmdWeB = (List<String>) getPatch().getList("no_protect_cmd.command_b", new ArrayList<String>());
		this.spyCommand = (List<String>) getPatch().getList("spy_command.command_list", new ArrayList<String>());

		this.regionMessageProtect = getPatch().getBoolean("protect_message");
		this.regionMessageProtectWe = getPatch().getBoolean("protect_we_message");
		this.spyCommandNotifyConsole = getPatch().getBoolean("spy_command.notify.console");
		this.spyCommandNotifyAdmin = getPatch().getBoolean("spy_command.notify.admin");
		this.spyCommandNotifyAdminPlaySoundEnable = getPatch().getBoolean("spy_command.notify.admin.sound");
		this.spyCommandNotifyAdminPlaySound = getPatch().getString("spy_command.notify.admin.sound.type");
	}
	private ConfigurationSection getPatch() {return WorldGuardRegionProtect.instance.getConfig().getConfigurationSection("worldguard_protect_region.");}
}