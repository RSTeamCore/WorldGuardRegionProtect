package net.rsteamcore.wgrp.rslibs.datasource;

import net.rsteamcore.wgrp.rslibs.api.RegionAction;

import java.util.UUID;

public interface StorageDataSource {

	boolean load();
	void loadAsync();
	void setLogAction(String nickname, UUID uniqueId, long time, RegionAction action, String region, String world, int x, int y, int z);
	void close();
	void reload();
}
