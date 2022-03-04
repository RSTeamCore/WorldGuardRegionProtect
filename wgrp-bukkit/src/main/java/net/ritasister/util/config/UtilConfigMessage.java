package net.ritasister.util.config;

import net.ritasister.rslibs.api.ChatApi;
import net.ritasister.rslibs.utils.annotatedyaml.Annotations;
import net.ritasister.rslibs.utils.annotatedyaml.Configuration;

public class UtilConfigMessage extends Configuration {

	@Annotations.Final
	@Annotations.Comment("Version of config, do not touch!")
	@Annotations.Key("config-message-version")
	private final String configVersion = "1.0";

	public String getConfigVersion() {
		return configVersion;
	}

	//Main message
	@Annotations.Key("wgrpMsg")
	private final String wgrpMsg = "&8[&cWGRP&8] &aThis region is protect by server!";

	@Annotations.Key("wgrpMsgWe")
	private final String wgrpMsgWe = "&8[&cWGRP&8] &cYou can''t used WorldEdit command here!";

	@Annotations.Key("noPerm")
	private final String noPerm = "&cYou don't have permissions to use this command.";

	@Annotations.Key("sendAdminInfoIfUsedCommandWithRG")
	private final String sendAdminInfoIfUsedCommandWithRG = "&8[&4!&8] &cAttention! &e<player> &cused command &5<cmd> &cin region: &3<region>";

	@Annotations.Key("sendAdminInfoIfBreakInRegion")
	private final String sendAdminInfoIfBreakInRegion = "&8[&4!&8] &cAttention! &b[<time>] &e<player> &6break &cblock in region: &3<region> &cLocation &3<x> <y> <z> <world>";

	@Annotations.Key("sendAdminInfoIfPlaceInRegion")
	private final String sendAdminInfoIfPlaceInRegion = "&8[&4!&8] &cAttention! &b[<time>] &e<player> &6place &cblock in region: &3<region> &cLocation &3<x> <y> <z> <world>";

	@Annotations.Key("example-use-command.wgrpUseHelp")
	private final String wgrpUseHelp = "&aUse: &e/wgrp help";

	//Config
	@Annotations.Key("configs.configReloaded")
	private final String configReloaded = "&8[&cWGRP&8] &aFile &6config.yml &areload success!";

	@Annotations.Key("configs.configNotFound")
	private final String configNotFound = "&8[&cWGRP&8] &cFile &6config.yml &cnot found, created new!";

	@Annotations.Key("configs.configMsgReloaded")
	private final String configMsgReloaded = "&8[&cWGRP&8] &aFile &6messages.yml &areload success!";

	@Annotations.Key("configs.configMsgNotFound")
	private final String configMsgNotFound = "&8[&cWGRP&8] &cFile &6messages.yml &cnot found, created new!";

	//Message for database
	@Annotations.Key("database.dbConnectSuccessfull")
	private final String dbConnectSuccessfull = "Connection to the database successful.";

	@Annotations.Key("database.dbConnectError")
	private final String dbConnectError = "Failed connect to the database. Plugin will be stopped!";

	@Annotations.Key("database.dbConnectFailed")
	private final String dbConnectFailed = "Failed connect to the database!";

	@Annotations.Key("database.dbReconnect")
	private final String dbReconnect = "Lost connection to the database! An attempt will be made to reconnect.";

	@Annotations.Key("database.dbClosePSTError")
	private final String dbClosePSTError = "PreparedStatement Close Error!";

	@Annotations.Key("database.dbCloseRSError")
	private final String dbCloseRSError = "ResultSet Close Error!";

	@Annotations.Key("database.dbCloseDBError")
	private final String dbCloseDBError = "Database closing error!";

	@Annotations.Key("database.dbLoadError")
	private final String dbLoadError = "Error loading data from the database!";

	@Annotations.Key("database.dbLoadAsyncError")
	private final String dbLoadAsyncError = "Error when asynchronously loading data from the database!";

	//Plural time start.
	@Annotations.Key("plural.pluralTimeEmpty")
	private final String pluralTimeEmpty = "0 second";


	@Annotations.Key("plural.pluralDay1")
	private final String pluralDay1 = "day";

	@Annotations.Key("plural.pluralDay2")
	private final String pluralDay2 = "of the day";

	@Annotations.Key("plural.pluralDay3")
	private final String pluralDay3 = "days";


	@Annotations.Key("plural.pluralHour1")
	private final String pluralHour1 = "hour";

	@Annotations.Key("plural.pluralHour2")
	private final String pluralHour2 = "hours";

	@Annotations.Key("plural.pluralHour3")
	private final String pluralHour3 = "hours";


	@Annotations.Key("plural.pluralMinute1")
	private final String pluralMinute1 = "minute";

	@Annotations.Key("plural.pluralMinute2")
	private final String pluralMinute2 = "minute";

	@Annotations.Key("plural.pluralMinute3")
	private final String pluralMinute3 = "minutes";

	@Annotations.Key("plural.pluralMinute4")
	private final String pluralMinute4 = "minutes";


	@Annotations.Key("plural.pluralSecond1")
	private final String pluralSecond1 = "second";

	@Annotations.Key("plural.pluralSecond2")
	private final String pluralSecond2 = "second";

	@Annotations.Key("plural.pluralSecond3")
	private final String pluralSecond3 = "seconds";

	@Annotations.Key("plural.pluralSecond4")
	private final String pluralSecond4 = "seconds";
	//Plural time end

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
	//Message for database start
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
	//Message for database end

	//Plural time start
	public String getPluralTimeEmpty() {
		return pluralTimeEmpty;
	}


	public String getPluralDay1() {
		return pluralDay1;
	}

	public String getPluralDay2() {
		return pluralDay2;
	}

	public String getPluralDay3() {
		return pluralDay3;
	}


	public String getPluralHour1() {
		return pluralHour1;
	}

	public String getPluralHour2() {
		return pluralHour2;
	}

	public String getPluralHour3() {
		return pluralHour3;
	}


	public String getPluralMinute1() {
		return pluralMinute1;
	}

	public String getPluralMinute2() {
		return pluralMinute2;
	}

	public String getPluralMinute3() {
		return pluralMinute3;
	}

	public String getPluralMinute4() {
		return pluralMinute4;
	}


	public String getPluralSecond1() {
		return pluralSecond1;
	}

	public String getPluralSecond2() {
		return pluralSecond2;
	}

	public String getPluralSecond3() {
		return pluralSecond3;
	}

	public String getPluralSecond4() {
		return pluralSecond4;
	}
	//Plural time end


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
				//Message for database start
				"dbConnectSuccessfull='" + dbConnectSuccessfull + '\'' +
				"dbConnectError='" + dbConnectError + '\'' +
				"dbConnectFailed='" + dbConnectFailed + '\'' +
				"dbReconnect='" + dbReconnect + '\'' +
				"dbClosePSTError='" + dbClosePSTError + '\'' +
				"dbCloseRSError='" + dbCloseRSError + '\'' +
				"dbCloseDBError='" + dbCloseDBError + '\'' +
				"dbLoadError='" + dbLoadError + '\'' +
				//Message for database end
				//Plural time start
				"dbLoadAsyncError='" + pluralTimeEmpty + '\'' +
				"dbLoadAsyncError='" + pluralDay1 + '\'' +
				"dbLoadAsyncError='" + pluralDay2 + '\'' +
				"dbLoadAsyncError='" + pluralDay3 + '\'' +
				"dbLoadAsyncError='" + pluralHour1 + '\'' +
				"dbLoadAsyncError='" + pluralHour2 + '\'' +
				"dbLoadAsyncError='" + pluralHour3 + '\'' +
				"dbLoadAsyncError='" + pluralMinute1 + '\'' +
				"dbLoadAsyncError='" + pluralMinute2 + '\'' +
				"dbLoadAsyncError='" + pluralMinute3 + '\'' +
				"dbLoadAsyncError='" + pluralMinute4 + '\'' +
				"dbLoadAsyncError='" + pluralSecond1 + '\'' +
				"dbLoadAsyncError='" + pluralSecond2 + '\'' +
				"dbLoadAsyncError='" + pluralSecond3 + '\'' +
				"dbLoadAsyncError='" + pluralSecond4 + '\'' +
				//Plural time end
				'}';
	}
}