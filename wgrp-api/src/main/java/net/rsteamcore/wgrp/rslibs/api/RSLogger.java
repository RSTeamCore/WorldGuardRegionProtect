package net.rsteamcore.wgrp.rslibs.api;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class RSLogger {

	private final Logger ROOT_LOGGER_NAME = Logger.getLogger("WGRP");

	public void info(final @NotNull String message) {
		ROOT_LOGGER_NAME.warning(message);
	}

	public void warn(final @NotNull String message) {
		ROOT_LOGGER_NAME.warning(message);
	}

	public void error(final @NotNull String message) {
		ROOT_LOGGER_NAME.severe(message);
	}

}