package net.ritasister.util.config;

import net.ritasister.rslibs.api.ChatApi;
import net.ritasister.rslibs.api.RSApi;
import org.bukkit.configuration.ConfigurationSection;

import net.ritasister.util.UtilLoadConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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

	public String dbConnectSuccessfull;
	public String dbConnectError;
	public String dbConnectFailed;
	public String dbReconnect;
	public String dbClosePSTError;
	public String dbCloseRSError;
	public String dbCloseDBError;
	public String dbLoadError;
	public String dbLoadAsyncError;

	public UtilConfigMessage() {
		try{
			wgrpMsg = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.ServerMsg.wgrpMsg", wgrpMsg));
			wgrpMsgWe = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.ServerMsg.wgrpMsg_We", wgrpMsgWe));
			noPerm = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.ServerMsg.noPerm", noPerm));
			sendAminInfo = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.ServerMsg.sendAminInfo", sendAminInfo));
			wgrpUseHelp = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp));

			configReloaded = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.Configs.configReloaded", configReloaded));
			configNotFound = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.Configs.configNotFound", configNotFound));
			configMsgReloaded = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.Configs.configMsgReloaded", configMsgReloaded));
			configMsgNotFound = ChatApi.getColorCode(RSApi.getStringMessageColor("messages.Configs.configMsgNotFound", configMsgNotFound));

			dbConnectSuccessfull=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbConnectSuccessfull", dbConnectSuccessfull));
			dbConnectError=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbConnectError", dbConnectError));
			dbConnectFailed=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbConnectFailed", dbConnectFailed));
			dbReconnect=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbReconnect", dbReconnect));
			dbClosePSTError=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbClosePSTError", dbClosePSTError));
			dbCloseRSError=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbCloseRSError", dbCloseRSError));
			dbCloseDBError=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbCloseDBError", dbCloseDBError));
			dbLoadError=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbLoadError", dbLoadError));
			dbLoadAsyncError=ChatApi.getColorCode(RSApi.getStringMessageColor("messages.database.dbLoadAsyncError", dbLoadAsyncError));
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}
}