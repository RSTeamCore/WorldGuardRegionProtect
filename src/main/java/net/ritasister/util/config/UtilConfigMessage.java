package net.ritasister.util.config;

import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.util.UtilLoadConfig;

public class UtilConfigMessage
{
	private UtilLoadConfig config;
	
	public String wgrpMsg;
	public String wgrpMsgWe;
	public String noPerm;
	public String sendAminInfo;
	
	public String wgrpUseHelp;
	
	public String configReloaded;
	public String configNotFound;
	public String configMsgReloaded;
	public String configMsgNotFound;
	
	public UtilConfigMessage()
	{
		this.config = WorldGuardRegionProtect.instance.utilLoadConfig;
		
		wgrpMsg = this.config.messages.getString("messages.ServerMsg.wgrpMsg", wgrpMsg).replaceAll("&", "§");
		wgrpMsgWe = this.config.messages.getString("messages.ServerMsg.wgrpMsg_We", wgrpMsgWe).replaceAll("&", "§");
		noPerm = this.config.messages.getString("messages.ServerMsg.noPerm", noPerm).replaceAll("&", "§");
		sendAminInfo = this.config.messages.getString("messages.ServerMsg.sendAminInfo", sendAminInfo).replaceAll("&", "§");
		
		wgrpUseHelp = this.config.messages.getString("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp).replaceAll("&", "§");
		
		configReloaded = this.config.messages.getString("messages.Configs.configReloaded", configReloaded).replaceAll("&", "§");
		configNotFound = this.config.messages.getString("messages.Configs.configNotFound", configNotFound).replaceAll("&", "§");
		configMsgReloaded = this.config.messages.getString("messages.Configs.configMsgReloaded", configMsgReloaded).replaceAll("&", "§");
		configMsgNotFound = this.config.messages.getString("messages.Configs.configMsgNotFound", configMsgNotFound).replaceAll("&", "§");
	}
}