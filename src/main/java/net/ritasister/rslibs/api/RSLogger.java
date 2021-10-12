package net.ritasister.rslibs.api;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RSLogger
{
	/*private static Logger logger;
	
	public RSLogger(String pluginName)
	{
		this.logger=LoggerFactory.getLogger(pluginName);
	}*/
	
	final static Logger logger = LoggerFactory.getLogger("RSLibrary-Logger");

	/*
	 * Send message with info.
	 * 
	 * @param msg
	 */
	public final static void info(final String msg) 
	{
		logger.info(msg.replace("&", "§"));
	}
	/*
	 * Send message with warn.
	 * 
	 * @param msg
	 */
	public final static void warn(final String msg) 
	{
		logger.warn(msg.replace("&", "§"));
	}
	/*
	 * Send message with error.
	 * 
	 * @param msg
	 */
	public final static void err(final String msg) 
	{
		logger.error(msg.replace("&", "§"));
	}
	/*
	 * Send message with about file.
	 * 
	 * @param file
	 */
	public final static void LoadConfigMsgSuccess(final File file)
	{
		RSLogger.info("&2Config: &a<config> &2loaded success!".replace("<config>", String.valueOf(file)));
	}
	/*
	 * Send message with about file.
	 * 
	 * @param message
	 */
	public final static void LoadConfigMsgError(final String message)
	{
		RSLogger.err("&cConfig: &4<msg_error> &ccould not load!".replace("<msg_error>", String.valueOf(message)));
	}
}