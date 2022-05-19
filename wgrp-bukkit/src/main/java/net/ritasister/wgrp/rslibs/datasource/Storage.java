package net.ritasister.wgrp.rslibs.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ritasister.wgrp.rslibs.api.Action;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Storage implements StorageDataSource {

	private final WorldGuardRegionProtect wgRegionProtect;

	private HikariDataSource ds;

	public Storage(WorldGuardRegionProtect worldGuardRegionProtect) {
		this.wgRegionProtect =worldGuardRegionProtect;
		this.connect();
		this.initialize();
	}

	public void connect() {
		HikariConfig config = new HikariConfig();
		config.setDriverClassName("org.mariadb.jdbc.Driver");
		config.setJdbcUrl("jdbc:mariadb://"
				+ wgRegionProtect.getUtilConfig().host + ":"
				+ wgRegionProtect.getUtilConfig().port + "/"
				+ wgRegionProtect.getUtilConfig().database);
		config.setUsername(wgRegionProtect.getUtilConfig().user);
		config.setPassword(wgRegionProtect.getUtilConfig().password);
		
		// Pool settings
        config.setMaximumPoolSize(wgRegionProtect.getUtilConfig().maxPoolSize);
        config.setMaxLifetime(wgRegionProtect.getUtilConfig().maxLifetime * 1000L);
        config.setConnectionTimeout(wgRegionProtect.getUtilConfig().connectionTimeout);
				
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
			wgRegionProtect.getRsApi().getLogger().error(wgRegionProtect.getUtilConfigMessage().dbReconnect);
			this.connect();
		}
		return this.ds.getConnection();
	}

	private void initialize() {
		PreparedStatement pst = null;
		try(Connection conn = Storage.this.getConnection()) {
			pst = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS <table> (id INT AUTO_INCREMENT, nickname VARCHAR(16) NULL, uniqueId VARCHAR(36) NULL, time BIGINT(20) NOT NULL DEFAULT '0', action VARCHAR(5) NULL, region VARCHAR(60) NULL, world VARCHAR(60), x DOUBLE NOT NULL DEFAULT '0', y DOUBLE NOT NULL DEFAULT '0', z DOUBLE NOT NULL DEFAULT '0', CONSTRAINT table_const_prim PRIMARY KEY (id));"
					.replace("<table>", wgRegionProtect.getUtilConfig().table));
			pst.execute();
			pst.close();
		}catch(SQLException ex){
			wgRegionProtect.getRsApi().getLogger().error(wgRegionProtect.getUtilConfigMessage().dbConnectFailed);
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
					.replace("<table>", wgRegionProtect.getUtilConfig().table));
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
				wgRegionProtect.getRsStorage().dbLogs.put(uniqueId, dataBase);
			}
			return true;
		}catch(SQLException ex){
			wgRegionProtect.getRsApi().getLogger().error(wgRegionProtect.getUtilConfigMessage().dbLoadError);
			ex.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pst);
		}return false;
	}

	public void loadAsync() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(wgRegionProtect.getWgrpBukkitPlugin(), () -> {
					ConcurrentHashMap<UUID, StorageDataBase> tempDataBase = new ConcurrentHashMap<>();
					PreparedStatement pst = null;
					ResultSet rs = null;
					try (Connection conn = Storage.this.getConnection()) {
						pst = conn.prepareStatement(
								"SELECT * FROM <table>;"
										.replace("<table>", wgRegionProtect.getUtilConfig().table));
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
						wgRegionProtect.getRsStorage().dbLogs = new ConcurrentHashMap<>(tempDataBase);
					} catch (SQLException ex) {
						wgRegionProtect.getRsApi().getLogger().error(wgRegionProtect.getUtilConfigMessage().dbLoadAsyncError);
						ex.printStackTrace();
					} finally {
						Storage.this.close(rs);
						Storage.this.close(pst);
					}
				}, wgRegionProtect.getUtilConfig().intervalReload * 20L,
				wgRegionProtect.getUtilConfig().intervalReload * 20L);
	}

	@Override
	public void setLogAction(String nickname, UUID uniqueId, long time, Action action, String region, String world, int x, int y, int z) {
		PreparedStatement pst = null;
		try(Connection conn = this.getConnection()) {
				pst = conn.prepareStatement("INSERT INTO <table> (nickname, uniqueId, time, action, region, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
						.replace("<table>", wgRegionProtect.getUtilConfig().table));
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
			wgRegionProtect.getRsApi().getLogger().error("[MySQL] <id> "+uniqueId.toString()
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
			wgRegionProtect.getRsApi().getLogger().error(wgRegionProtect.getUtilConfigMessage().dbClosePSTError);
		}
	}

	public void close(final ResultSet rs) {
		try{
			if (rs != null) {
				rs.close();
			}
		}catch(SQLException ex){
			wgRegionProtect.getRsApi().getLogger().error(wgRegionProtect.getUtilConfigMessage().dbCloseRSError);
		}
	}

	public void reload() {
        if (ds != null) {
			ds.close();
		}
        connect();
		wgRegionProtect.getRsApi().getLogger().info(wgRegionProtect.getUtilConfigMessage().dbConnectSuccessfull);
    }
	
	@Override
    public void close() {
		if (ds != null && !ds.isClosed()) {
			ds.close();
		}
	}
}