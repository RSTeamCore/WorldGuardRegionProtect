package net.ritasister.rslibs.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ritasister.rslibs.api.Action;
import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.util.config.UtilConfigMessage;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class Storage implements StorageDataSource {

	private final WorldGuardRegionProtect worldGuardRegionProtect;
	private final UtilConfig utilConfig;
	private final UtilConfigMessage utilConfigMessage;

	private HikariDataSource ds;

	public Storage(WorldGuardRegionProtect worldGuardRegionProtect, UtilConfig utilConfig, UtilConfigMessage utilConfigMessage) {
		this.worldGuardRegionProtect=worldGuardRegionProtect;
		this.utilConfig=utilConfig;
		this.utilConfigMessage=utilConfigMessage;
		this.connect();
		this.initialize();
	}

	public void connect() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl("jdbc:mariadb://"
				+ utilConfig.host + ":"
				+ utilConfig.port + "/"
				+ utilConfig.database);
		config.setUsername(utilConfig.user);
		config.setPassword(utilConfig.password);
		
		// Pool settings
        config.setMaximumPoolSize(utilConfig.maxPoolSize);
        config.setMaxLifetime(utilConfig.maxLifetime * 1000L);
        config.setConnectionTimeout(utilConfig.connectionTimeout);
				
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
			RSLogger.err(utilConfigMessage.dbReconnect);
			this.connect();
		}
		return this.ds.getConnection();
	}

	private void initialize() {
		PreparedStatement pst = null;
		try(Connection conn = Storage.this.getConnection()) {
			pst = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS <table> (id INT AUTO_INCREMENT, nickname VARCHAR(16) NULL, uniqueId VARCHAR(36) NULL, time BIGINT(20) NOT NULL DEFAULT '0', action VARCHAR(5) NULL, region VARCHAR(60) NULL, world VARCHAR(60), x DOUBLE NOT NULL DEFAULT '0', y DOUBLE NOT NULL DEFAULT '0', z DOUBLE NOT NULL DEFAULT '0', CONSTRAINT table_const_prim PRIMARY KEY (id));"
					.replace("<table>", utilConfig.table));
			pst.execute();
			pst.close();
		}catch(SQLException ex){
			RSLogger.err(utilConfigMessage.dbConnectFailed);
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
					.replace("<table>", utilConfig.table));
			rs = pst.executeQuery();
			while(rs.next()) {
				UUID uniqueId = UUID.fromString(rs.getString("uniqueId"));
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
				worldGuardRegionProtect.rsStorage.dbLogs.put(uniqueId, dataBase);
			}
			return true;
		}catch(SQLException ex){
			RSLogger.err(utilConfigMessage.dbLoadError);
			ex.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pst);
		}return false;
	}

	public void loadAsync() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(worldGuardRegionProtect, () -> {
					ConcurrentHashMap<UUID, StorageDataBase> tempDataBase = new ConcurrentHashMap<>();
					PreparedStatement pst = null;
					ResultSet rs = null;
					try (Connection conn = Storage.this.getConnection()) {
						pst = conn.prepareStatement(
								"SELECT * FROM <table>;"
										.replace("<table>", utilConfig.table));
						rs = pst.executeQuery();
						while (rs.next()) {
							UUID uniqueId = UUID.fromString(rs.getString("uniqueId"));
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
							tempDataBase.put(uniqueId, dataBase);
						}
						worldGuardRegionProtect.rsStorage.dbLogs = new ConcurrentHashMap<>(tempDataBase);
					} catch (SQLException ex) {
						RSLogger.err(utilConfigMessage.dbLoadAsyncError);
						ex.printStackTrace();
					} finally {
						Storage.this.close(rs);
						Storage.this.close(pst);
					}
				}, utilConfig.intervalReload * 20L,
				utilConfig.intervalReload * 20L);
	}

	@Override
	public void setLogAction(String nickname, UUID uniqueId, long time, Action action, String region, String world, int x, int y, int z) {
		PreparedStatement pst = null;
		try(Connection conn = this.getConnection()) {
				pst = conn.prepareStatement("INSERT INTO <table> (nickname, uniqueId, time, action, region, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
						.replace("<table>", utilConfig.table));
				pst.setString(1, nickname);
				pst.setString(2, uniqueId.toString());
				pst.setLong(3, time);
				pst.setString(4, action.toString());
				pst.setString(5, region);
				pst.setString(6, world);
				pst.setDouble(7, x);
				pst.setDouble(8, y);
				pst.setDouble(9, z);
				pst.executeUpdate();
			} catch (SQLException ex) {
			RSLogger.err("[MySQL] <id> "+uniqueId.toString()
					.replace("<id>", uniqueId.toString())+ ex);
		} finally {
			this.close(pst);
		}
	}

	public void close(final PreparedStatement pst) {
		try{
			if(pst != null) {
				pst.close();
			}
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbClosePSTError);
		}
	}

	public void close(final ResultSet rs) {
		try{
			if (rs != null) {
				rs.close();
			}
		}catch(SQLException ex){
			RSLogger.err(WorldGuardRegionProtect.utilConfigMessage.dbCloseRSError);
		}
	}
	
	public void reload() {
        if (ds != null) {
			ds.close();
		}
        connect();
		RSLogger.info(WorldGuardRegionProtect.utilConfigMessage.dbConnectSuccessfull);
    }
	
	@Override
    public void close() {
		if (ds != null && !ds.isClosed()) {
			ds.close();
		}
	}
}