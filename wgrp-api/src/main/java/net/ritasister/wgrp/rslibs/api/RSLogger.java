package net.ritasister.wgrp.rslibs.api;

import org.bukkit.Bukkit;
import java.util.logging.Logger;

public class RSLogger {

	private final Logger ROOT_LOGGER_NAME = Logger.getLogger("WGRP");

	public void info(final String message) {
		ROOT_LOGGER_NAME.info(message);
		//Bukkit.getConsoleSender().sendMessage("");
	}

	public void warn(final String message) {
		ROOT_LOGGER_NAME.warning(message);
	}

	public void error(final String message) {
		ROOT_LOGGER_NAME.severe(message);
	}
}