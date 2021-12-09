package net.ritasister.util.config;

import net.ritasister.rslibs.api.ChatApi;
import org.bukkit.configuration.ConfigurationSection;

import net.ritasister.util.UtilLoadConfig;

public class UtilConfigMessage {
	public String wgrpMsg;
	public String wgrpMsgWe;
	public String noPerm;
	public String sendAminInfo;

	public String wgrpUseHelp;

	public String configReloaded;
	public String configNotFound;
	public String configMsgReloaded;
	public String configMsgNotFound;

	public UtilConfigMessage() {
		try{
			wgrpMsg = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.wgrpMsg", wgrpMsg));
			wgrpMsgWe = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.wgrpMsg_We", wgrpMsgWe));
			noPerm = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.noPerm", noPerm));
			sendAminInfo = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.sendAminInfo", sendAminInfo));
			wgrpUseHelp = ChatApi.getColorCode(getMessageConfig().getString("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp));

			configReloaded = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configReloaded", configReloaded));
			configNotFound = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configNotFound", configNotFound));
			configMsgReloaded = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configMsgReloaded", configMsgReloaded));
			configMsgNotFound = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configMsgNotFound", configMsgNotFound));
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
	private ConfigurationSection getMessageConfig() {return UtilLoadConfig.messages;}
}