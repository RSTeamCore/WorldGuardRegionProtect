package net.ritasister.rslibs.datasource;

import java.sql.SQLException;
import java.util.UUID;

public interface StorageDataSource {

	boolean load();
	void loadAsync();
	void logAction(String nickname, UUID uniqueId, long time, String action, String region, String world, Double x, Double y, Double z);
	void close();
	void reload();
}