package net.ritasister.util.config;

import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.util.UtilLoadConfig;

public class UtilConfigMessage
{
	private UtilLoadConfig config;
	
	public String srpMsg;
	public String srpWeMsg;
	
	public String noPerm;
	public String srpUseHelp;
	
	public String configReloaded;
	public String configNotFound;
	public String configMsgReloaded;
	public String configMsgNotFound;
	
	public UtilConfigMessage()
	{
		this.config = WorldGuardRegionProtect.instance.utilLoadConfig;
		
		srpWeMsg = this.config.messages.getString("messages.ServerMsg.srp_msg_we", srpWeMsg).replaceAll("&", "§");
		srpMsg = this.config.messages.getString("messages.ServerMsg.srp_msg", srpMsg).replaceAll("&", "§");
		noPerm = this.config.messages.getString("messages.ServerMsg.noperm", noPerm).replaceAll("&", "§");
		
		srpUseHelp = this.config.messages.getString("messages.Example_Use_Command.srp_use_help", srpUseHelp).replaceAll("&", "§");
		
		configReloaded = this.config.messages.getString("messages.Configs.config_reloaded", configReloaded).replaceAll("&", "§");
		configNotFound = this.config.messages.getString("messages.Configs.config_not_found", configNotFound).replaceAll("&", "§");
		configMsgReloaded = this.config.messages.getString("messages.Configs.config_msg_reloaded", configMsgReloaded).replaceAll("&", "§");
		configMsgNotFound = this.config.messages.getString("messages.Configs.config_msg_not_found", configMsgNotFound).replaceAll("&", "§");
	}
}