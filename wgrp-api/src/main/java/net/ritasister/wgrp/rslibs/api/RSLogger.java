package net.ritasister.wgrp.rslibs.api;

import net.kyori.adventure.text.TextComponent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

public class RSLogger {

	private final Logger ROOT_LOGGER_NAME = Logger.getLogger("WGRP");

	public void info(final @NotNull TextComponent message) {
		ROOT_LOGGER_NAME.info(message.toString());
	}

	public void warn(final @NotNull TextComponent message) {
		ROOT_LOGGER_NAME.warning(message.toString());
	}

	public void error(final @NotNull TextComponent message) {
		ROOT_LOGGER_NAME.severe(message.toString());
	}
}