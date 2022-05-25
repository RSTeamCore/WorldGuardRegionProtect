package net.ritasister.wgrp.rslibs.api;

import java.io.File;
import java.util.logging.Logger;

public class RSLogger {

	private final Logger ROOT_LOGGER_NAME = Logger.getLogger("WGRP");
	private final ChatApi chatApi;

	public RSLogger(ChatApi chatApi) {
		this.chatApi=chatApi;
	}

	public void info(final String message) {
		ROOT_LOGGER_NAME.info(chatApi.getColorCode(message));
	}

	public void warn(final String message) {
		ROOT_LOGGER_NAME.warning(chatApi.getColorCode(message));
	}

	public void error(final String message) {
		ROOT_LOGGER_NAME.severe(chatApi.getColorCode(message));
	}

	public void loadConfigMsgSuccess(final File fileName) {
		this.info("&2Config: &a<fileName> &2loaded success!"
				.replace("<fileName>", String.valueOf(fileName)));
	}
	public void updateConfigMsgSuccess(final File fileName) {
		this.info("&2Config: &a<fileName> &2has been updated. Please check new updated fields in <fileName>"
				.replace("<fileName>", String.valueOf(fileName)));
	}
}