package net.ritasister.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.util.UtilLoadConfig;

public class UtilConfigMessage
{
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
		UtilLoadConfig config = WorldGuardRegionProtect.instance.utilLoadConfig;
		try{
			wgrpMsg = config.messages.getString("messages.ServerMsg.wgrpMsg", wgrpMsg).replace("&", "§");
			wgrpMsgWe = config.messages.getString("messages.ServerMsg.wgrpMsg_We", wgrpMsgWe).replace("&", "§");
			noPerm = config.messages.getString("messages.ServerMsg.noPerm", noPerm).replace("&", "§");
			sendAminInfo = config.messages.getString("messages.ServerMsg.sendAminInfo", sendAminInfo).replace("&", "§");

			wgrpUseHelp = config.messages.getString("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp).replace("&", "§");

			configReloaded = config.messages.getString("messages.Configs.configReloaded", configReloaded).replace("&", "§");
			configNotFound = config.messages.getString("messages.Configs.configNotFound", configNotFound).replace("&", "§");
			configMsgReloaded = config.messages.getString("messages.Configs.configMsgReloaded", configMsgReloaded).replace("&", "§");
			configMsgNotFound = config.messages.getString("messages.Configs.configMsgNotFound", configMsgNotFound).replace("&", "§");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}