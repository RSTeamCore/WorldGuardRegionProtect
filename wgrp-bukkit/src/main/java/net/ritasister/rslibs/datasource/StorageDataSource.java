package net.ritasister.rslibs.datasource;

import java.sql.SQLException;
import java.util.UUID;

public interface StorageDataSource {

	boolean load();
	void loadAsync();
	void setUniqueID(String nickname, UUID uniqueId);
	String getPlayerName(final UUID uniqueId) throws SQLException;
	UUID getPlayerUniqueId(String username) throws SQLException;
	void close();
	void reload();
}