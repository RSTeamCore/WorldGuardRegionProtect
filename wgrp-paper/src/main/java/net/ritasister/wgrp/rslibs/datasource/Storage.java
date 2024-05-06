package net.ritasister.wgrp.rslibs.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.RegionAction;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//TODO need to change...
@SuppressWarnings({"checkstyle:AbbreviationAsWordInName"})
public class Storage implements StorageDataSource {

	private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private final Config config;

	private HikariDataSource ds;

	public Storage(@NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
		this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
		this.connect();
		this.initialize();
	}

	private void connect() {
		HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mariadb://"
				+ config.getMySQLSettings().getHost() + ":"
				+ config.getMySQLSettings().getPort() + "/"
				+ config.getMySQLSettings().getDataBase());
        hikariConfig.setUsername(config.getMySQLSettings().getUser());
        hikariConfig.setPassword(config.getMySQLSettings().getPassword());

		// Pool settings
        hikariConfig.setMaximumPoolSize(config.getMySQLSettings().getMaxPoolSize());
        hikariConfig.setMaxLifetime(config.getMySQLSettings().getMaxLifetime() * 1000L);
        hikariConfig.addDataSourceProperty("useSSL", config.getMySQLSettings().getUseSsl());
        hikariConfig.setConnectionTimeout(config.getMySQLSettings().getConnectionTimeout());

        hikariConfig.setPoolName("MariaDBPool");

        // Encoding
        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
        hikariConfig.addDataSourceProperty("useUnicode", "true");

        // Random stuff
        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("jdbcCompliantTruncation", "false");

        // Caching
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "275");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		ds = new HikariDataSource(hikariConfig);
	}

	private Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	private void initialize() {
		PreparedStatement pst = null;
		try(Connection conn = Storage.this.getConnection()) {
			pst = conn.prepareStatement(
					"CREATE TABLE IF NOT EXISTS <table> (id INT AUTO_INCREMENT, nickname VARCHAR(16) NULL, uniqueId VARCHAR(36) NULL, time BIGINT(20) NOT NULL DEFAULT '0', action VARCHAR(5) NULL, region VARCHAR(60) NULL, world VARCHAR(60), x DOUBLE NOT NULL DEFAULT '0', y DOUBLE NOT NULL DEFAULT '0', z DOUBLE NOT NULL DEFAULT '0', CONSTRAINT table_const_prim PRIMARY KEY (id));"
					.replace("<table>", config.getMySQLSettings().getTable()));
			pst.execute();
			pst.close();
		} catch(SQLException ex){
            wgrpBukkitPlugin.getPluginLogger().severe("Failed connect to database! Error code: " + ex.getErrorCode());
		} finally {
			this.close(pst);
		}
	}

	@Override
	public boolean load() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try(Connection conn = this.getConnection()) {
			pst = conn.prepareStatement("SELECT * FROM <table>;"
					.replace("<table>", config.getMySQLSettings().getTable()));
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
				wgrpBukkitPlugin.getRsStorage().dbLogs.put(uniqueId, dataBase);
			}
			return true;
		}catch(SQLException ex){
            wgrpBukkitPlugin.getPluginLogger().severe("Failed to load from database!");
			ex.printStackTrace();
		}finally{
			this.close(rs);
			this.close(pst);
		}return false;
	}

	public void loadAsync() {
		Bukkit.getScheduler().runTaskTimerAsynchronously(wgrpBukkitPlugin.getWgrpBukkitBase(), () -> {
			ConcurrentHashMap<UUID, StorageDataBase> tempDataBase = new ConcurrentHashMap<>();
			PreparedStatement pst = null;
			ResultSet rs = null;
			try (Connection conn = Storage.this.getConnection()) {
				pst = conn.prepareStatement(
						"SELECT * FROM <table>;"
								.replace("<table>", config.getMySQLSettings().getTable()));
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
                wgrpBukkitPlugin.getRsStorage().dbLogs = new ConcurrentHashMap<>(tempDataBase);
			} catch (SQLException ex) {
                wgrpBukkitPlugin.getPluginLogger().severe("Failed to load database asynchronous!");
				ex.printStackTrace();
			} finally {
				this.close(rs);
				this.close(pst);
			}
			}, config.getMySQLSettings().getIntervalReload() * 20L,
				config.getMySQLSettings().getIntervalReload() * 20L);
	}

	@Override
	public void setLogAction(String nickname, @NotNull UUID uniqueId, long time, @NotNull RegionAction action, String region, String world, int x, int y, int z) {
		Bukkit.getScheduler().runTaskAsynchronously(wgrpBukkitPlugin.getWgrpBukkitBase(), () -> {
			PreparedStatement pst = null;
			try(Connection conn = this.getConnection()) {
				pst = conn.prepareStatement("INSERT INTO <table> (nickname, uniqueId, time, action, region, world, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
						.replace("<table>", config.getMySQLSettings().getTable()));
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
                wgrpBukkitPlugin.getPluginLogger().severe("[DataBase] <id> " + uniqueId.toString()
					.replace("<id>", uniqueId.toString())+ ex);
			} finally {
				this.close(pst);
			}
		});
	}

	public void close(final PreparedStatement pst) {
		try{
			if(pst != null) {
				pst.close();
			}
		}catch(SQLException ex){
            wgrpBukkitPlugin.getPluginLogger().severe("Failed to close prepared statement");
		}
	}

	public void close(final ResultSet rs) {
		try{
			if (rs != null) {
				rs.close();
			}
		}catch(SQLException ex){
            wgrpBukkitPlugin.getPluginLogger().severe("Failed to close result set");
		}
	}

	public void reload() {
        if (ds != null) {
			ds.close();
		}
        connect();
        wgrpBukkitPlugin.getPluginLogger().info("Successfully reloaded!");
    }

	@Override
    public void close() {
		if (ds != null && !ds.isClosed()) {
			ds.close();
		}
	}
}
