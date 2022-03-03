package net.ritasister.util.config;

import net.ritasister.rslibs.api.ChatApi;
import net.ritasister.rslibs.utils.annotatedyaml.Annotations.*;
import net.ritasister.rslibs.utils.annotatedyaml.Configuration;

public class UtilConfigMessage extends Configuration {

	@Final
	@Comment("Version of config, do not touch!")
	@Key("config-message-version")
	private final String configVersion = "1.0";

	public String getConfigVersion() {
		return configVersion;
	}

	//Main message
	@Key("wgrpMsg")
	private final String wgrpMsg = "&8[&cWGRP&8] &aThis region is protect by server!";

	@Key("wgrpMsgWe")
	private final String wgrpMsgWe = "&8[&cWGRP&8] &cYou can''t used WorldEdit command here!";

	@Key("noPerm")
	private final String noPerm = "&cYou don't have permissions to use this command.";

	@Key("sendAdminInfoIfUsedCommandWithRG")
	private final String sendAdminInfoIfUsedCommandWithRG = "&8[&4!&8] &cAttention! &e<player> &cused command &5<cmd> &cin region: &3<rg>";

	@Key("sendAdminInfoIfBreakInRegion")
	private final String sendAdminInfoIfBreakInRegion = "&8[&4!&8] &cAttention! &e<player> &6break &cblock in region: &3<rg>";

	@Key("sendAdminInfoIfPlaceInRegion")
	private final String sendAdminInfoIfPlaceInRegion = "&8[&4!&8] &cAttention! &e<player> &6place &cblock in region: &3<rg>";

	@Key("example-use-command.wgrpUseHelp")
	private final String wgrpUseHelp = "&aUse: &e/wgrp help";

	//Config
	@Key("configs.configReloaded")
	private final String configReloaded = "&8[&cWGRP&8] &aFile &6config.yml &areload success!";

	@Key("configs.configNotFound")
	private final String configNotFound = "&8[&cWGRP&8] &cFile &6config.yml &cnot found, created new!";

	@Key("configs.configMsgReloaded")
	private final String configMsgReloaded = "&8[&cWGRP&8] &aFile &6messages.yml &areload success!";

	@Key("configs.configMsgNotFound")
	private final String configMsgNotFound = "&8[&cWGRP&8] &cFile &6messages.yml &cnot found, created new!";

	//Message for database
	@Key("database.dbConnectSuccessfull")
	private final String dbConnectSuccessfull = "Connection to the database successful.";

	@Key("database.dbConnectError")
	private final String dbConnectError = "Failed connect to the database. Plugin will be stopped!";

	@Key("database.dbConnectFailed")
	private final String dbConnectFailed = "Failed connect to the database!";

	@Key("database.dbReconnect")
	private final String dbReconnect = "Lost connection to the database! An attempt will be made to reconnect.";

	@Key("database.dbClosePSTError")
	private final String dbClosePSTError = "PreparedStatement Close Error!";

	@Key("database.dbCloseRSError")
	private final String dbCloseRSError = "ResultSet Close Error!";

	@Key("database.dbCloseDBError")
	private final String dbCloseDBError = "Database closing error!";

	@Key("database.dbLoadError")
	private final String dbLoadError = "Error loading data from the database!";

	@Key("database.dbLoadAsyncError")
	private final String dbLoadAsyncError = "Error when asynchronously loading data from the database!";

	//Main message
	public String wgrpMsg() {
		return ChatApi.getColorCode(wgrpMsg);
	}

	public String wgrpMsgWe() {
		return ChatApi.getColorCode(wgrpMsgWe);
	}

	public String noPerm() {
		return ChatApi.getColorCode(noPerm);
	}

	public String sendAdminInfoIfUsedCommandWithRG() {
		return ChatApi.getColorCode(sendAdminInfoIfUsedCommandWithRG);
	}

	public String sendAdminInfoIfBreakInRegion() {
		return ChatApi.getColorCode(sendAdminInfoIfBreakInRegion);
	}

	public String sendAdminInfoIfPlaceInRegion() {
		return ChatApi.getColorCode(sendAdminInfoIfPlaceInRegion);
	}

	public String getWgrpUseHelp() {
		return ChatApi.getColorCode(wgrpUseHelp);
	}
	//Config
	public String getConfigReloaded() {
		return ChatApi.getColorCode(configReloaded);
	}
	public String getConfigNotFound() {
		return ChatApi.getColorCode(configNotFound);
	}
	public String getConfigMsgReloaded() {
		return ChatApi.getColorCode(configMsgReloaded);
	}
	public String getConfigMsgNotFound() {
		return ChatApi.getColorCode(configMsgNotFound);
	}
	//Message for database
	public String dbConnectSuccessfull() {
		return ChatApi.getColorCode(dbConnectSuccessfull);
	}

	public String dbConnectError() {
		return ChatApi.getColorCode(dbConnectError);
	}

	public String dbConnectFailed() {
		return ChatApi.getColorCode(dbConnectFailed);
	}

	public String dbReconnect() {
		return ChatApi.getColorCode(dbReconnect);
	}

	public String dbClosePSTError() {
		return ChatApi.getColorCode(dbClosePSTError);
	}

	public String dbCloseRSError() {
		return ChatApi.getColorCode(dbCloseRSError);
	}

	public String dbCloseDBError() {
		return ChatApi.getColorCode(dbCloseDBError);
	}

	public String dbLoadError() {
		return ChatApi.getColorCode(dbLoadError);
	}

	public String dbLoadAsyncError () {
		return ChatApi.getColorCode(dbLoadAsyncError);
	}

	@Override
	public String toString() {
		return "Messages{" +
				"configVersion='" + configVersion + '\'' +
				"wgrpMsg='" + wgrpMsg + '\'' +
				"wgrpMsgWe='" + wgrpMsgWe + '\'' +
				"noPerm='" + noPerm + '\'' +
				"sendAdminInfoIfUsedCommandWithRG='" + sendAdminInfoIfUsedCommandWithRG + '\'' +
				"sendAdminInfoIfBreakInRegion='" + sendAdminInfoIfBreakInRegion + '\'' +
				"sendAdminInfoIfPlaceInRegion='" + sendAdminInfoIfPlaceInRegion + '\'' +
				"configReloaded='" + configReloaded + '\'' +
				"configNotFound='" + configNotFound + '\'' +
				"configMsgReloaded='" + configMsgReloaded + '\'' +
				"configMsgNotFound='" + configMsgNotFound + '\'' +
				"wgrpUseHelp='" + wgrpUseHelp + '\'' +
				"dbConnectSuccessfull='" + dbConnectSuccessfull + '\'' +
				"dbConnectError='" + dbConnectError + '\'' +
				"dbConnectFailed='" + dbConnectFailed + '\'' +
				"dbReconnect='" + dbReconnect + '\'' +
				"dbClosePSTError='" + dbClosePSTError + '\'' +
				"dbCloseRSError='" + dbCloseRSError + '\'' +
				"dbCloseDBError='" + dbCloseDBError + '\'' +
				"dbLoadError='" + dbLoadError + '\'' +
				"dbLoadAsyncError='" + dbLoadAsyncError + '\'' +
				'}';
	}
}