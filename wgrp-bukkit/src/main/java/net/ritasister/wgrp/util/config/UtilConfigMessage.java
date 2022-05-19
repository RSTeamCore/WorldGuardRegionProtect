package net.ritasister.wgrp.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class UtilConfigMessage {

	private final WorldGuardRegionProtect wgRegionProtect;

	public String configMsgVersion = "1.0";

	public String wgrpMsg = "";
	public String wgrpMsgWe = "";
	public String noPerm = "";

	public String wgrpUseHelp = "";

	public String configReloaded = "";
	public String configNotFound = "";
	public String configMsgReloaded = "";
	public String configMsgNotFound = "";

	public String sendAdminInfoIfUsedCommandInRG = "";
	public String sendAdminInfoIfActionInRegion = "";

	public String pluralDay1 = "";
	public String pluralDay2 = "";
	public String pluralDay3 = "";

	public String pluralHour1 = "";
	public String pluralHour2 = "";
	public String pluralHour3 = "";

	public String pluralMinute1 = "";
	public String pluralMinute2 = "";
	public String pluralMinute3 = "";
	public String pluralMinute4 = "";

	public String pluralSecond1 = "";
	public String pluralSecond2 = "";
	public String pluralSecond3 = "";
	public String pluralSecond4 = "";

	public String pluralTimeEmpty = "";

	public String dbConnectSuccessfull = "Connection to the database successful.";
	public String dbConnectError = "Failed connect to the database. Plugin will be stopped!";
	public String dbConnectFailed = "Failed connect to the database!";
	public String dbReconnect = "Lost connection to the database! An attempt will be made to reconnect.";
	public String dbClosePSTError = "PreparedStatement Close Error!";
	public String dbCloseRSError = "ResultSet Close Error!";
	public String dbCloseDBError = "Database closing error!" ;
	public String dbLoadError = "Error loading data from the database!";
	public String dbLoadAsyncError = "Error when asynchronously loading data from the database!";

	public UtilConfigMessage(WorldGuardRegionProtect wgRegionProtect) {
		this.wgRegionProtect = wgRegionProtect;
		try {
			wgrpMsg = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.ServerMsg.wgrpMsg", wgrpMsg));
			wgrpMsgWe = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.ServerMsg.wgrpMsgWe", wgrpMsgWe));
			noPerm = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.ServerMsg.noPerm", noPerm));
			wgrpUseHelp = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp));

			configReloaded = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.Configs.configReloaded", configReloaded));
			configNotFound = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.Configs.configNotFound", configNotFound));
			configMsgReloaded = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.Configs.configMsgReloaded", configMsgReloaded));
			configMsgNotFound = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.Configs.configMsgNotFound", configMsgNotFound));

			sendAdminInfoIfUsedCommandInRG = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.Notify.sendAdminInfoIfUsedCommandInRG", sendAdminInfoIfUsedCommandInRG));
			sendAdminInfoIfActionInRegion = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.Notify.sendAdminInfoIfActionInRegion", sendAdminInfoIfActionInRegion));

			pluralDay1 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.day.pluralDay1", pluralDay1));
			pluralDay2 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.day.pluralDay2", pluralDay2));
			pluralDay3 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.day.pluralDay3", pluralDay3));

			pluralHour1 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.hour.pluralHour1", pluralHour1));
			pluralHour2 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.hour.pluralHour2", pluralHour2));
			pluralHour3 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.hour.pluralHour3", pluralHour3));

			pluralMinute1 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute1", pluralMinute1));
			pluralMinute2 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute2", pluralMinute2));
			pluralMinute3 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute3", pluralMinute3));
			pluralMinute4 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute4", pluralMinute4));

			pluralSecond1 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond1", pluralSecond1));
			pluralSecond2 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond2", pluralSecond2));
			pluralSecond3 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond3", pluralSecond3));
			pluralSecond4 = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond4", pluralSecond4));

			pluralTimeEmpty = wgRegionProtect.getChatApi().getColorCode(getMessagePatch().getString("messages.PluralTime.timeEmpty.pluralTimeEmpty", pluralTimeEmpty));
		}catch(IllegalArgumentException e){
			wgRegionProtect.getRsApi().getLogger().warn("&cPlease update your config!!!");
		}
	}

	@NotNull
	public String getConfigVersion() {
		return configMsgVersion;
	}

	private ConfigurationSection getMessagePatch() {
		return wgRegionProtect.getUtilLoadConfig().messages;
	}
}