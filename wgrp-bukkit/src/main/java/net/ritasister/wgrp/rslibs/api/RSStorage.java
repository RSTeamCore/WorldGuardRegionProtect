package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.rslibs.datasource.StorageDataBase;
import net.ritasister.wgrp.rslibs.datasource.StorageDataSource;
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
