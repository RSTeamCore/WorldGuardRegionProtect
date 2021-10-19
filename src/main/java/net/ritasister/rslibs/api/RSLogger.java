package net.ritasister.rslibs.api;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSLogger
{
	//static Logger logger;
	final static Logger logger = LoggerFactory.getLogger("RSLibrary-Logger");
	
	/*public RSLogger(String pluginName)
	{
		LoggerFactory.getLogger(pluginName);
	}*/

	//public static Logger logger = LoggerFactory.getLogger("RSLibrary-Logger");

	/**
	 * Send message with info.
	 *
	 */
	public static void info(final String msg) {logger.warn(Color.getColorCode(msg));}
	/**
	 * Send message with warn.
	 *
	 */
	public static void warn(final String msg) {logger.warn(Color.getColorCode(msg));}
	/**
	 * Send message with error.
	 *
	 */
	public static void err(final String msg)
	{
		logger.error(Color.getColorCode(msg));
	}
	/**
	 * Send message with about file.
	 *
	 */
	public static void LoadConfigMsgSuccess(final String fileName)
	{
		RSLogger.info("&2Config: &a<config> &2loaded success!".replace("<config>", fileName));
	}
}