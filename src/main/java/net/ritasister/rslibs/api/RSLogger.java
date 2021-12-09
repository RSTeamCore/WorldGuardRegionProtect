package net.ritasister.rslibs.api;

import java.io.File;
import java.util.logging.Logger;

public class RSLogger {
	final static Logger logger = Logger.getLogger("RSLibrary-Logger");

	//public static Logger logger = LoggerFactory.getLogger("RSLibrary-Logger");

	/**
	 * Send message with info.
	 *
	 */
	public static void info(final String msg) {logger.info(ChatApi.getColorCode(msg));}
	/**
	 * Send message with warn.
	 *
	 */
	public static void warn(final String msg) {logger.warning(ChatApi.getColorCode(msg));}
	/**
	 * Send message with error.
	 *
	 */
	public static void err(final String msg)
	{
		logger.severe(ChatApi.getColorCode(msg));
	}
	/**
	 * Send message with about file.
	 *
	 */
	public static void LoadConfigMsgSuccess(final File fileName) {
		RSLogger.info("&2Config: &a<config> &2loaded success!".replace("<config>", String.valueOf(fileName)));
	}
}