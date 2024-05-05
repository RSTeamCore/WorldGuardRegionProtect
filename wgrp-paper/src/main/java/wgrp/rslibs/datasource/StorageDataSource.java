package wgrp.rslibs.datasource;

import wgrp.rslibs.RegionAction;

import java.util.UUID;

//TODO need to change...
public interface StorageDataSource {

	boolean load();
	void loadAsync();
	void setLogAction(String nickname, UUID uniqueId, long time, RegionAction action, String region, String world, int x, int y, int z);
	void close();
	void reload();
}
