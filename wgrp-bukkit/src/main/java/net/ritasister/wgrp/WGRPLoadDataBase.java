package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import org.bukkit.Bukkit;


@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WGRPLoadDataBase {

    private final WorldGuardRegionProtect worldGuardRegionProtect;

    public void loadDataBase() {
        RSStorage rsStorage = worldGuardRegionProtect.getWgrpContainer().getRsStorage();
        if (worldGuardRegionProtect.getWgrpContainer().getConfigLoader().getConfig().getDataBaseEnable()) {
            final long durationTimeStart = System.currentTimeMillis();
            rsStorage.dbLogsSource = new Storage(worldGuardRegionProtect);
            rsStorage.dbLogs.clear();
            if (rsStorage.dbLogsSource.load()) {
                WGRPContainer.getLogger().info("[DataBase] The database is loaded.");
                this.postEnable();
                WGRPContainer.getLogger().info(String.format(
                        "[DataBase] Startup duration: %s ms.", System.currentTimeMillis() - durationTimeStart));
            }
        }
    }

    public void postEnable() {
        Bukkit.getServer().getScheduler().cancelTasks(this.worldGuardRegionProtect.getWGRPBukkitPlugin());
        if (worldGuardRegionProtect.getWgrpContainer().getConfigLoader().getConfig().getMySQLSettings().getIntervalReload() > 0) {
            worldGuardRegionProtect.getWgrpContainer().getRsStorage().dbLogsSource.loadAsync();
            WGRPContainer.getLogger().info("[DataBase] The database is loaded asynchronously.");
        }
    }

}
