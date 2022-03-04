package net.ritasister.datasources;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public class Storage implements StorageDataSource {

	private HikariDataSource ds;

	public Storage() {
		this.connect();
		this.initialize();
	}

	public void connect() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl("jdbc:mariadb://"
				+ WorldGuardRegionProtect.instance.utilConfig.host() + ":"
				+ WorldGuardRegionProtect.instance.utilConfig.port() + "/"
				+ WorldGuardRegionProtect.instance.utilConfig.database());
		config.setUsername(WorldGuardRegionProtect.instance.utilConfig.user());
		config.setPassword(WorldGuardRegionProtect.instance.utilConfig.password());
		
		// Pool settings
        config.setMaximumPoolSize(WorldGuardRegionProtect.instance.utilConfig.maxPoolSize());
        config.setMaxLifetime(WorldGuardRegionProtect.instance.utilConfig.maxLifetime() * 1000L);
        config.setConnectionTimeout(WorldGuardRegionProtect.instance.utilConfig.connectionTimeout());
				
		config.setPoolName("MariaDBPool");
		
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

	public Connection getConnection() throws SQLException {
		if (!this.ds.getConnection().isValid(3)) {
			RSLogger.err(WorldGuardRegionProtect.instance.utilConfigMessage.dbReconnect());
			this.connect();
		}
		return this.ds.getConnection();
	}

	private void initialize() {
		PreparedStatement pst = null;
		try(Connection conn = Storage.this.getConnection()) {
			pst = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS <tables> (id INT AUTO_INCREMENT, nickname VARCHAR(16) NOT NULL UNIQUE, uuid VARCHAR(36) NOT NULL UNIQUE, IPAddress VARCHAR(15) NULL, temperature INT(4) NOT NULL DEFAULT '0', parkour_passed INT(4) NOT NULL DEFAULT '0', world VARCHAR(60), x DOUBLE NOT NULL DEFAULT '0', y DOUBLE NOT NULL DEFAULT '0', z DOUBLE NOT NULL DEFAULT '0', yaw FLOAT NOT NULL DEFAULT '0', pitch FLOAT NOT NULL DEFAULT '0', CONSTRAINT table_const_prim PRIMARY KEY (id, nickname, uuid));"
					.replace("<tables>", WorldGuardRegionProtect.instance.utilConfig.tables()));
			pst.execute();
			pst.close();
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.instance.utilConfigMessage.dbConnectFailed());
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
					.replace("<tables>", WorldGuardRegionProtect.instance.utilConfig.tables()));
			rs = pst.executeQuery();
			while(rs.next()) {
				UUID uniqueId = UUID.fromString(rs.getString("uuid"));
				StorageDataBase dataBase = new StorageDataBase(
						rs.getInt("id"), 
						rs.getString("nickname"), 
						uniqueId);
				WorldGuardRegionProtect.dbLogs.put(uniqueId, dataBase);
			}
			return true;
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.instance.utilConfigMessage.dbLoadError());
			ex.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pst);
		}return false;
	}
	public void loadAsync() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(WorldGuardRegionProtect.instance, () -> {
			final HashMap<UUID, StorageDataBase> tmp_players = new HashMap<>();
			PreparedStatement pst = null;
			ResultSet rs = null;
			try(Connection conn = Storage.this.getConnection()) {
				pst = conn.prepareStatement(
						"SELECT * FROM <tables>;"
						.replace("<tables>", WorldGuardRegionProtect.instance.utilConfig.tables()));
				rs = pst.executeQuery();
				while(rs.next()) {
					UUID uniqueId = UUID.fromString(rs.getString("uuid"));
					StorageDataBase current_dataBase = (StorageDataBase)WorldGuardRegionProtect.dbLogs.get(uniqueId);
					StorageDataBase dataBase = new StorageDataBase(
							rs.getInt("id"),
							rs.getString("nickname"),
							uniqueId);
					tmp_players.put(uniqueId, dataBase);
				}
				WorldGuardRegionProtect.dbLogs = new HashMap<>(tmp_players);
			}catch(SQLException ex){
				RSLogger.err(WorldGuardRegionProtect.instance.utilConfigMessage.dbLoadAsyncError());
				ex.printStackTrace();
			}finally{
				Storage.this.close(rs);
				Storage.this.close(pst);
			}
		}, WorldGuardRegionProtect.instance.utilConfig.intervalReload() * 20L,
				WorldGuardRegionProtect.instance.utilConfig.intervalReload() * 20L);
	}

	@Override
	public void setUniqueID(String nickname, UUID uniqueId) {

	}

	@Override
	public String getPlayerName(UUID uniqueId) {
		return null;
	}

	@Override
	public UUID getPlayerUniqueId(String username) {
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