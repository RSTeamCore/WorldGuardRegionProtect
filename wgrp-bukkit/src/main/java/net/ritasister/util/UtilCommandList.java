package net.ritasister.util;

public enum UtilCommandList {

	WGRP("worldguardregionprotect");

	private final String slot;

	UtilCommandList(String slot) {
		this.slot = slot;
	}

	public String getSlot() {
		return slot;
	}

	//public static final String worldGuardRegionProtect=("worldguardregionprotect");
}