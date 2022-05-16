package net.ritasister.util.config;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class UtilConfigMessage {

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

	public UtilConfigMessage() {
		try {
			wgrpMsg = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.ServerMsg.wgrpMsg", wgrpMsg));
			wgrpMsgWe = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.ServerMsg.wgrpMsgWe", wgrpMsgWe));
			noPerm = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.ServerMsg.noPerm", noPerm));
			wgrpUseHelp = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.ExampleUseCommand.wgrpUseHelp", wgrpUseHelp));

			configReloaded = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.Configs.configReloaded", configReloaded));
			configNotFound = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.Configs.configNotFound", configNotFound));
			configMsgReloaded = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.Configs.configMsgReloaded", configMsgReloaded));
			configMsgNotFound = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.Configs.configMsgNotFound", configMsgNotFound));

			sendAdminInfoIfUsedCommandInRG = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.Notify.sendAdminInfoIfUsedCommandInRG", sendAdminInfoIfUsedCommandInRG));
			sendAdminInfoIfActionInRegion = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.Notify.sendAdminInfoIfActionInRegion", sendAdminInfoIfActionInRegion));

			pluralDay1 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.day.pluralDay1", pluralDay1));
			pluralDay2 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.day.pluralDay2", pluralDay2));
			pluralDay3 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.day.pluralDay3", pluralDay3));

			pluralHour1 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.hour.pluralHour1", pluralHour1));
			pluralHour2 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.hour.pluralHour2", pluralHour2));
			pluralHour3 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.hour.pluralHour3", pluralHour3));

			pluralMinute1 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute1", pluralMinute1));
			pluralMinute2 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute2", pluralMinute2));
			pluralMinute3 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute3", pluralMinute3));
			pluralMinute4 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.minute.pluralMinute4", pluralMinute4));

			pluralSecond1 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond1", pluralSecond1));
			pluralSecond2 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond2", pluralSecond2));
			pluralSecond3 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond3", pluralSecond3));
			pluralSecond4 = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.second.pluralSecond4", pluralSecond4));

			pluralTimeEmpty = WorldGuardRegionProtect.getInstance().chatApi.getColorCode(getMessagePatch().getString("messages.PluralTime.timeEmpty.pluralTimeEmpty", pluralTimeEmpty));
		}catch(IllegalArgumentException e){
			WorldGuardRegionProtect.getInstance().getRsApi().getLogger().warn("&ePlease update your config.");
		}
	}

	@NotNull
	public String getConfigVersion() {
		return configMsgVersion;
	}

	private ConfigurationSection getMessagePatch() {
		return WorldGuardRegionProtect.getInstance().getUtilLoadConfig().messages;
	}
}