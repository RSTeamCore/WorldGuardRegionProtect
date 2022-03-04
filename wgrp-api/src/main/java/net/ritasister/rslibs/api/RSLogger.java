package net.ritasister.rslibs.api;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class RSLogger {
	final static Logger logger = Logger.getLogger("RSLibrary-Logger");

	public static void info(final String msg) {
		logger.info(ChatApi.getColorCode(msg));
	}

	public static void warn(final String msg) {
		logger.warning(ChatApi.getColorCode(msg));
	}

	public static void warn(final String msg, IOException exception) {
		logger.warning(ChatApi.getColorCode(msg));
	}

	public static void warn(final String msg, Exception exception) {
		logger.warning(ChatApi.getColorCode(msg));
	}

	public static void err(final String msg) {
		logger.severe(ChatApi.getColorCode(msg));
	}

	public static void err(final String msg, Exception exception) {
		logger.severe(ChatApi.getColorCode(msg));
	}

	public static void LoadConfigMsgSuccess(final File fileName) {
		RSLogger.info("&2Config: &a<config> &2loaded success!".replace("<config>", String.valueOf(fileName)));
	}
}