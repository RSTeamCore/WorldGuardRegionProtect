package net.ritasister.rslibs.datasource;

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
				+ WorldGuardRegionProtect.utilConfig.host + ":"
				+ WorldGuardRegionProtect.utilConfig.port + "/"
				+ WorldGuardRegionProtect.utilConfig.database);
		config.setUsername(WorldGuardRegionProtect.utilConfig.user);
		config.setPassword(WorldGuardRegionProtect.utilConfig.password);
		
		// Pool settings
        config.setMaximumPoolSize(WorldGuardRegionProtect.utilConfig.maxPoolSize);
        config.setMaxLifetime(WorldGuardRegionProtect.utilConfig.maxLifetime * 1000L);
        config.setConnectionTimeout(WorldGuardRegionProtect.utilConfig.connectionTimeout);
				
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
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbReconnect);
			this.connect();
		}
		return this.ds.getConnection();
	}

	private void initialize() {
		PreparedStatement pst = null;
		try(Connection conn = Storage.this.getConnection()) {
			pst = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS <table> (id INT AUTO_INCREMENT, nickname VARCHAR(16) NOT NULL UNIQUE, uniqueId VARCHAR(36) NOT NULL UNIQUE, time BIGINT(20) NOT NULL DEFAULT '0', action VARCHAR(5) NULL, region VARCHAR(60) NULL, world VARCHAR(60), x DOUBLE NOT NULL DEFAULT '0', y DOUBLE NOT NULL DEFAULT '0', z DOUBLE NOT NULL DEFAULT '0', CONSTRAINT table_const_prim PRIMARY KEY (id, nickname, uniqueId));"
					.replace("<table>", WorldGuardRegionProtect.utilConfig.table));
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
			pst = conn.prepareStatement("SELECT * FROM <table>;"
					.replace("<table>", WorldGuardRegionProtect.utilConfig.table));
			rs = pst.executeQuery();
			while(rs.next()) {
				UUID uniqueId = UUID.fromString(rs.getString("uuid"));
				StorageDataBase dataBase = new StorageDataBase(
						rs.getInt("id"),
						rs.getString("nickname"),
						uniqueId,
						rs.getLong("time"),
						rs.getString("action"),
						rs.getString("region"),
						rs.getString("world"),
						rs.getDouble("x"),
						rs.getDouble("y"),
						rs.getDouble("z"));
				WorldGuardRegionProtect.dbLogs.put(uniqueId, dataBase);
			}
			return true;
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbLoadError);
			ex.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pst);
		}return false;
	}
	public void loadAsync() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(WorldGuardRegionProtect.getInstance(), () -> {
					final HashMap<UUID, StorageDataBase> tmp_players = new HashMap<>();
					PreparedStatement pst = null;
					ResultSet rs = null;
					try (Connection conn = Storage.this.getConnection()) {
						pst = conn.prepareStatement(
								"SELECT * FROM <table>;"
										.replace("<table>", WorldGuardRegionProtect.utilConfig.table));
						rs = pst.executeQuery();
						while (rs.next()) {
							UUID uniqueId = UUID.fromString(rs.getString("uuid"));
							StorageDataBase current_dataBase = WorldGuardRegionProtect.dbLogs.get(uniqueId);
							StorageDataBase dataBase = new StorageDataBase(
									rs.getInt("id"),
									rs.getString("nickname"),
									uniqueId,
									rs.getLong("time"),
									rs.getString("action"),
									rs.getString("region"),
									rs.getString("world"),
									rs.getDouble("x"),
									rs.getDouble("y"),
									rs.getDouble("z"));
							tmp_players.put(uniqueId, dataBase);
						}
						WorldGuardRegionProtect.dbLogs = new HashMap<>(tmp_players);
					} catch (SQLException ex) {
						RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbLoadAsyncError);
						ex.printStackTrace();
					} finally {
						Storage.this.close(rs);
						Storage.this.close(pst);
					}
				}, WorldGuardRegionProtect.utilConfig.intervalReload * 20L,
				WorldGuardRegionProtect.utilConfig.intervalReload * 20L);
	}

	@Override
	public void logAction(String nickname, UUID uniqueId, long time, String action, String region, String world, Double x, Double y, Double z) {
		PreparedStatement pst = null;
		try(Connection conn = this.getConnection()) {
				pst = conn.prepareStatement("INSERT INTO <table> (nickname, uniqueId, time, action, region, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
						.replace("<table>", WorldGuardRegionProtect.utilConfig.table));
				pst.setString(1, nickname);
				pst.setString(2, uniqueId.toString());
				pst.setLong(3, time);
				pst.setString(4, action);
				pst.setString(5, region);
				pst.setString(6, world);
				pst.setDouble(7, x);
				pst.setDouble(8, y);
				pst.setDouble(9, z);
				pst.executeUpdate();
			} catch (SQLException ex) {
			RSLogger.err("[MySQL]  <id> "+uniqueId.toString()
					.replace("<id>", uniqueId.toString())+ ex);
		} finally {
			this.close(pst);
		}
	}

	public void close(final PreparedStatement pst) {
		try{
			if(pst != null) {pst.close();}
		}catch(SQLException ex){
			RSLogger.err("[MariaDB|PlayerData] Failed to end preparedstatement!");
		}
	}

	public void close(final ResultSet rs) {
		try{
			if (rs != null) {rs.close();}
		}catch(SQLException ex){
			RSLogger.err("[MariaDB|PlayerData] Failed to end resultset!");
		}
	}
	
	public void reload() {
        if (ds != null) {
			ds.close();
		}
        connect();
		RSLogger.info("[MariaDB|PlayerData] Hikari ConnectionPool arguments reloaded!");
    }
	
	@Override
    public void close() {
		if (ds != null && !ds.isClosed()) {
			ds.close();
		}
	}
}