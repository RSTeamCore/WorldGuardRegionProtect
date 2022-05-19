package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.rslibs.api.interfaces.IRSLogger;

import java.io.File;

public class RSLogger implements IRSLogger {

	private final ChatApi chatApi;

	public RSLogger(ChatApi chatApi) {
		this.chatApi=chatApi;
	}

	public void info(final String msg) {
		ROOT_LOGGER_NAME.info(chatApi.getColorCode(msg));
	}

	public void warn(final String msg) {
		ROOT_LOGGER_NAME.warning(chatApi.getColorCode(msg));
	}

	public void error(final String msg) {
		ROOT_LOGGER_NAME.severe(chatApi.getColorCode(msg));
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