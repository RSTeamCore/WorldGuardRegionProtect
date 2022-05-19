package net.ritasister.wgrp.util;

public enum UtilCommandList {

	WGRP("worldguardregionprotect");

	private final String slot;

	UtilCommandList(String slot) {
		this.slot = slot;
	}

	public String getCommand() {
		return slot;
	}

}