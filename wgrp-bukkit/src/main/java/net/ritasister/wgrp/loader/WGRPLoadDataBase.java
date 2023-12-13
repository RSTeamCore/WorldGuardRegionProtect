package net.ritasister.wgrp.loader;

import lombok.extern.slf4j.Slf4j;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class WGRPLoadDataBase {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;
    private final Config config;

    public WGRPLoadDataBase(final @NotNull WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();

    }

    public void loadDataBase() {
        RSStorage rsStorage = wgrpBukkitPlugin.getRsStorage();
        if (config.getDataBaseEnable()) {
            final long durationTimeStart = System.currentTimeMillis();
            rsStorage.dbLogsSource = new Storage(wgrpBukkitPlugin);
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
        Bukkit.getServer().getScheduler().cancelTasks(wgrpBukkitPlugin.getWgrpBukkitBase());
        if (config.getMySQLSettings().getIntervalReload() > 0) {
            wgrpBukkitPlugin.getRsStorage().dbLogsSource.loadAsync();
            log.info("[DataBase] The database is loaded asynchronously.");
        }
    }

}
