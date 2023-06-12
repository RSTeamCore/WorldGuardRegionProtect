package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import org.bukkit.Bukkit;


@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
@Slf4j
public class WGRPLoadDataBase {

    private final WorldGuardRegionProtect worldGuardRegionProtect;

    public void loadDataBase() {
        RSStorage rsStorage = worldGuardRegionProtect.getWgrpContainer().getRsStorage();
        if (worldGuardRegionProtect.getWgrpContainer().getConfigLoader().getConfig().getDataBaseEnable()) {
            final long durationTimeStart = System.currentTimeMillis();
            rsStorage.dbLogsSource = new Storage(worldGuardRegionProtect);
            rsStorage.dbLogs.clear();
            if (rsStorage.dbLogsSource.load()) {
                log.info("[DataBase] The database is loaded.");
                this.postEnable();
                log.info(String.format(
                        "[DataBase] Startup duration: %s ms.", System.currentTimeMillis() - durationTimeStart));
            }
        }
    }

    public void postEnable() {
        Bukkit.getServer().getScheduler().cancelTasks(this.worldGuardRegionProtect.getWGRPBukkitPlugin());
        if (worldGuardRegionProtect.getWgrpContainer().getConfigLoader().getConfig().getMySQLSettings().getIntervalReload() > 0) {
            worldGuardRegionProtect.getWgrpContainer().getRsStorage().dbLogsSource.loadAsync();
            log.info("[DataBase] The database is loaded asynchronously.");
        }
    }

}
