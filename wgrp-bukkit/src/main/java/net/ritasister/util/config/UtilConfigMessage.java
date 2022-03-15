package net.ritasister.util.config;

import net.ritasister.rslibs.api.ChatApi;
import net.ritasister.util.UtilLoadConfig;
import org.bukkit.configuration.ConfigurationSection;

public class UtilConfigMessage {

	public String configVersion;

	public String wgrpMsg;
	public String wgrpMsgWe;
	public String noPerm;
	public String sendAminInfo;

	public String wgrpUseHelp;

	public String configReloaded;
	public String configNotFound;
	public String configMsgReloaded;
	public String configMsgNotFound;

	public String sendAdminInfoIfUsedCommandWithRG;
	public String sendAdminInfoIfBreakInRegion;
	public String sendAdminInfoIfPlaceInRegion;

	public String pluralDay1;
	public String pluralDay2;
	public String pluralDay3;

	public String pluralHour1;
	public String pluralHour2;
	public String pluralHour3;

	public String pluralMinute1;
	public String pluralMinute2;
	public String pluralMinute3;
	public String pluralMinute4;

	public String pluralSecond1;
	public String pluralSecond2;
	public String pluralSecond3;
	public String pluralSecond4;

	public String pluralTimeEmpty;

	public String dbConnectSuccessfull = "Connection to the database successful.";
	public String dbConnectError = "Failed connect to the database. Plugin will be stopped!";
	public String dbConnectFailed = "Failed connect to the database!";
	public String dbReconnect = "Lost connection to the database! An attempt will be made to reconnect.";
	public String dbClosePSTError = "PreparedStatement Close Error!";
	public String dbCloseRSError = "ResultSet Close Error!";
	public String dbCloseDBError = "Database closing error!";
	public String dbLoadError = "Error loading data from the database!";
	public String dbLoadAsyncError = "Error when asynchronously loading data from the database!";

	public UtilConfigMessage() {
		try{
			configVersion = getMessageConfig().getString("messages.config_version");

			wgrpMsg = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.wgrpMsg", wgrpMsg));
			wgrpMsgWe = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.wgrpMsg_We", wgrpMsgWe));
			noPerm = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.noPerm", noPerm));
			sendAminInfo = ChatApi.getColorCode(getMessageConfig().getString("messages.ServerMsg.sendAminInfo", sendAminInfo));
			wgrpUseHelp = ChatApi.getColorCode(getMessageConfig().getString("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp));

			configReloaded = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configReloaded", configReloaded));
			configNotFound = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configNotFound", configNotFound));
			configMsgReloaded = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configMsgReloaded", configMsgReloaded));
			configMsgNotFound = ChatApi.getColorCode(getMessageConfig().getString("messages.Configs.configMsgNotFound", configMsgNotFound));

			sendAdminInfoIfUsedCommandWithRG = ChatApi.getColorCode(getMessageConfig().getString("messages.Notify.sendAdminInfoIfUsedCommandWithRG", configMsgNotFound));
			sendAdminInfoIfBreakInRegion = ChatApi.getColorCode(getMessageConfig().getString("messages.Notify.sendAdminInfoIfBreakInRegion", configMsgNotFound));
			sendAdminInfoIfPlaceInRegion = ChatApi.getColorCode(getMessageConfig().getString("messages.Notify.sendAdminInfoIfPlaceInRegion", configMsgNotFound));

			pluralDay1 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.day.pluralDay1", wgrpMsg));
			pluralDay2 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.day.pluralDay2", wgrpMsg));
			pluralDay3 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.day.pluralDay3", wgrpMsg));

			pluralHour1 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.hour.pluralHour1", wgrpMsg));
			pluralHour2 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.hour.pluralHour2", wgrpMsg));
			pluralHour3 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.hour.pluralHour3", wgrpMsg));

			pluralMinute1 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.minute.pluralMinute1", wgrpMsg));
			pluralMinute2 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.minute.pluralMinute2", wgrpMsg));
			pluralMinute3 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.minute.pluralMinute3", wgrpMsg));
			pluralMinute4 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.minute.pluralMinute4", wgrpMsg));

			pluralSecond1 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.second.pluralSecond1", wgrpMsg));
			pluralSecond2 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.second.pluralSecond2", wgrpMsg));
			pluralSecond3 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.second.pluralSecond3", wgrpMsg));
			pluralSecond4 = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.second.pluralSecond4", wgrpMsg));

			pluralTimeEmpty = ChatApi.getColorCode(getMessageConfig().getString("messages.PluralTime.timeEmpty.pluralTimeEmpty", pluralTimeEmpty));
		}catch(NullPointerException ex){
			ex.printStackTrace();
		}
	}

	public String getConfigVersion() {
		return "1.1";
	}

	private ConfigurationSection getMessageConfig() {
		return UtilLoadConfig.messages;
	}
}