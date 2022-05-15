package net.ritasister.rslibs.api;

import net.ritasister.rslibs.datasource.StorageDataBase;
import net.ritasister.rslibs.datasource.StorageDataSource;
import net.ritasister.util.config.UtilConfig;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RSStorage {

    //DataBase
    public StorageDataSource dbLogsSource;
    public ConcurrentHashMap<UUID, StorageDataBase> dbLogs = new ConcurrentHashMap<>();

    /**
     * @param uniqueId get player uuid from storage.
     *
     * @return getDataStorage.
     */
    @NotNull
    public StorageDataBase getDataStorage(UUID uniqueId) {
        return this.dbLogs.get(uniqueId);
    }

    /**
     *
     * @return getDataSource.
     */
    @NotNull
    public StorageDataSource getDataSource() {
        return this.dbLogsSource;
    }
}
