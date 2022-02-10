package net.ritasister.util.config;

import net.ritasister.rslibs.api.RSApi;

public class UtilDataStorage {

    public String database;
    public String jdbcDriver;
    public String host;
    public String port;
    public String user;
    public String password;
    public String tables;

    public boolean useSsl;

    public int maxPoolSize;
    public int maxLifetime;
    public int connectionTimeout;
    public int intervalReloadPlayers;

    public UtilDataStorage() {
        this.database = RSApi.getPatch().getString("dataSource.database");
        this.jdbcDriver = RSApi.getPatch().getString("dataSource.jdbcDriver");
        this.host = RSApi.getPatch().getString("dataSource.host");
        this.port = RSApi.getPatch().getString("dataSource.port");
        this.user = RSApi.getPatch().getString("dataSource.user");
        this.password = RSApi.getPatch().getString("dataSource.password");
        this.tables = RSApi.getPatch().getString("dataSource.tables.tableName");
        this.useSsl = RSApi.getPatch().getBoolean("dataSource.useSsl");
        //pool settings
        this.maxPoolSize = RSApi.getPatch().getInt("dataSource.poolSettings.maxPoolSize");
        this.maxLifetime = RSApi.getPatch().getInt("dataSource.poolSettings.maxLifetime");
        this.connectionTimeout = RSApi.getPatch().getInt("dataSource.poolSettings.connectionTimeout");
        this.intervalReloadPlayers = RSApi.getPatch().getInt("data_source.player_data.interval.reload_players", 60);
    }
}