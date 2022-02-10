package net.ritasister.rslibs.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.util.config.UtilDataStorage;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Storage implements StorageDataSource {

	private HikariConfig config;
	private HikariDataSource ds;

	public Storage() {
		this.connect();
		this.initialize();
	}

	public void connect() {
		config = new HikariConfig();
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl("jdbc:mariadb://" 
				+ WorldGuardRegionProtect.utilDataStorage.host + ":"
				+ WorldGuardRegionProtect.utilDataStorage.port + "/"
				+ WorldGuardRegionProtect.utilDataStorage.database);
		config.setUsername(WorldGuardRegionProtect.utilDataStorage.user);
		config.setPassword(WorldGuardRegionProtect.utilDataStorage.password);
		
		// Pool settings
        config.setMaximumPoolSize(WorldGuardRegionProtect.utilDataStorage.maxPoolSize);
        config.setMaxLifetime(WorldGuardRegionProtect.utilDataStorage.maxLifetime * 1000L);
        config.setConnectionTimeout(WorldGuardRegionProtect.utilDataStorage.connectionTimeout);
				
		config.setPoolName("MariaSqlPool");
		
        // Encoding
        config.addDataSourceProperty("characterEncoding", "utf8");
        config.addDataSourceProperty("encoding", "UTF-8");
        config.addDataSourceProperty("useUnicode", "true");

        // Random stuff
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("jdbcCompliantTruncation", "false");

        // Caching
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "275");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
		ds = new HikariDataSource(config);
	}
	
	public Connection getConnection() throws SQLException {return this.ds.getConnection();}

	public void checkConnection() throws SQLException {
		if (!this.getConnection().isValid(3)) {
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbReconnect);
			this.connect();
		}
	}
	private void initialize() {
		PreparedStatement pst = null;
		try(Connection conn = Storage.this.getConnection()) {
			pst = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS <tables> (id INT AUTO_INCREMENT, nickname VARCHAR(16) NOT NULL UNIQUE, uuid VARCHAR(36) NOT NULL UNIQUE, IPAddress VARCHAR(15) NULL, temperature INT(4) NOT NULL DEFAULT '0', parkour_passed INT(4) NOT NULL DEFAULT '0', world VARCHAR(60), x DOUBLE NOT NULL DEFAULT '0', y DOUBLE NOT NULL DEFAULT '0', z DOUBLE NOT NULL DEFAULT '0', yaw FLOAT NOT NULL DEFAULT '0', pitch FLOAT NOT NULL DEFAULT '0', CONSTRAINT table_const_prim PRIMARY KEY (id, nickname, uuid));"
					.replace("<tables>", WorldGuardRegionProtect.utilDataStorage.tables));
			pst.execute();
			pst.close();
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbConnectFailed);
		}finally{
			this.close(pst);
		}
	}
	@Override
	public boolean load() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try(Connection conn = this.getConnection()) {
			pst = conn.prepareStatement("SELECT * FROM <tables>;"
					.replace("<tables>", WorldGuardRegionProtect.utilDataStorage.tables));
			rs = pst.executeQuery();
			while(rs.next()) {
				UUID uniqueId = UUID.fromString(rs.getString("uuid"));
				StorageDataBase dataBase = new StorageDataBase(
						rs.getInt("id"), 
						rs.getString("nickname"), 
						uniqueId);
				WorldGuardRegionProtect.dataBase.put(uniqueId, dataBase);
			}
			boolean var10 = true;
			return var10;
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbLoadError);
			ex.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pst);
		}return false;
	}
	public void loadAsync() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(WorldGuardRegionProtect.instance, () -> {
			final HashMap tmp_players = new HashMap();
			PreparedStatement pst = null;
			ResultSet rs = null;
			try(Connection conn = Storage.this.getConnection()) {
				pst = conn.prepareStatement(
						"SELECT * FROM <tables>;"
						.replace("<tables>", WorldGuardRegionProtect.utilDataStorage.tables));
				rs = pst.executeQuery();
				while(rs.next()) {
					UUID uniqueId = UUID.fromString(rs.getString("uuid"));
					StorageDataBase current_dataBase = (StorageDataBase)WorldGuardRegionProtect.dataBase.get(uniqueId);
					StorageDataBase dataBase = new StorageDataBase(
							rs.getInt("id"),
							rs.getString("nickname"),
							uniqueId);
					tmp_players.put(uniqueId, dataBase);
				}
				WorldGuardRegionProtect.dataBase = new HashMap(tmp_players);
			}catch(SQLException ex){
				RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbLoadAsyncError);
				ex.printStackTrace();
			}finally{
				Storage.this.close(rs);
				Storage.this.close(pst);
			}
		}, WorldGuardRegionProtect.utilDataStorage.intervalReloadPlayers * 20L, WorldGuardRegionProtect.utilDataStorage.intervalReloadPlayers * 20L);
	}

	@Override
	public void loadAsyncCheckAccountStatus() {

	}

	@Override
	public void setUniqueID(String nickname, UUID uniqueId) {

	}

	@Override
	public String getPlayerName(UUID uniqueId) throws SQLException {
		return null;
	}

	@Override
	public UUID getPlayerUniqueId(String username) throws SQLException {
		return null;
	}

	public void close(final PreparedStatement pst) {
		try{
			if(pst != null) {pst.close();}
		}catch(SQLException ex){
			RSLogger.err("[MariaDB|PlayerData] Не удалось завершить preparedstatement!");
		}
	}
	public void close(final ResultSet rs) {
		try{
			if (rs != null) {rs.close();}
		}catch(SQLException ex){
			RSLogger.err("[MariaDB|PlayerData] Не удалось завершить resultset!");
		}
	}
	
	public void reload() {
        if (ds != null) {ds.close();}
        connect();
		RSLogger.info("[MariaDB|PlayerData] Hikari ConnectionPool arguments reloaded!");
    }
	
	@Override
    public void close() {if (ds != null && !ds.isClosed()) {ds.close();}}
}