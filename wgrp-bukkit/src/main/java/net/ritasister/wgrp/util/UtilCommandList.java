package net.ritasister.wgrp.util;

public enum UtilCommandList {

	WGRP("worldguardregionprotect");

	private final String command;

	UtilCommandList(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

}