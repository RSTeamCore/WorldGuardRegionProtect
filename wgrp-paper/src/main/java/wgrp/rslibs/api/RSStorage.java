package wgrp.rslibs.api;

import org.jetbrains.annotations.NotNull;
import wgrp.rslibs.datasource.StorageDataBase;
import wgrp.rslibs.datasource.StorageDataSource;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RSStorage {

    //DataBase
    public StorageDataSource dbLogsSource;
    public Map<UUID, StorageDataBase> dbLogs = new ConcurrentHashMap<>();

    /**
     * @param uniqueId get player uuid from storage.
     * @return getDataStorage.
     */
    @NotNull
    public StorageDataBase getDataStorage(UUID uniqueId) {
        return this.dbLogs.get(uniqueId);
    }

    /**
     * @return getDataSource.
     */
    @NotNull
    public StorageDataSource getDataSource() {
        return this.dbLogsSource;
    }

}
