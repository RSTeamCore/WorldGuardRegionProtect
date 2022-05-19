package net.ritasister.wgrp;

import net.ritasister.rslibs.util.Metrics;
import net.ritasister.rslibs.util.UpdateChecker;
import net.ritasister.wgrp.rslibs.datasource.Storage;
import org.bukkit.plugin.java.JavaPlugin;

public class WGRPBukkitPlugin extends JavaPlugin {

    private final WorldGuardRegionProtect wgRegionProtect;

    public WGRPBukkitPlugin(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect = wgRegionProtect;
    }

    @Override
    public void onEnable() {
        this.getWgRegionProtect().load();
    }

    public void notifyPreBuild() {
        if(this.getWgRegionProtect().getPluginVersion().contains("alpha")
                && this.getWgRegionProtect().getPluginVersion().contains("pre")) {
            getWgRegionProtect().getRsApi().getLogger().warn("This is a test build. This building may be unstable!");
        } else {
            getWgRegionProtect().getRsApi().getLogger().info("This is the latest stable building.");
        }
    }

    protected void checkUpdate() {
        new UpdateChecker(this, 81321).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getWgRegionProtect().getRsApi().getLogger().info("&6==============================================");
                getWgRegionProtect().getRsApi().getLogger().info("&2Current version: &b<pl_ver>".replace("<pl_ver>", getWgRegionProtect().getPluginVersion()));
                getWgRegionProtect().getRsApi().getLogger().info("&2This is the latest version of plugin.");
                getWgRegionProtect().getRsApi().getLogger().info("&6==============================================");
            }else{
                getWgRegionProtect().getRsApi().getLogger().info("&6==============================================");
                getWgRegionProtect().getRsApi().getLogger().info("&eThere is a new version update available.");
                getWgRegionProtect().getRsApi().getLogger().info("&cCurrent version: &4<pl_ver>".replace("<pl_ver>", getWgRegionProtect().getPluginVersion()));
                getWgRegionProtect().getRsApi().getLogger().info("&3New version: &b<new_pl_ver>".replace("<new_pl_ver>", version));
                getWgRegionProtect().getRsApi().getLogger().info("&eDownload new version here:");
                getWgRegionProtect().getRsApi().getLogger().info("&ehttps://www.spigotmc.org/resources/worldguardregionprotect-1-13-1-17.81321/");
                getWgRegionProtect().getRsApi().getLogger().info("&6==============================================");
            }
        });
    }

    protected void loadDataBase() {
        if(this.getWgRegionProtect().getUtilConfig().databaseEnable) {
            final long duration_time_start = System.currentTimeMillis();
            this.getWgRegionProtect().getRsStorage().dbLogsSource = new Storage(this.getWgRegionProtect());
            this.getWgRegionProtect().getRsStorage().dbLogs.clear();
            if (this.getWgRegionProtect().getRsStorage().dbLogsSource.load()) {
                getWgRegionProtect(). getRsApi().getLogger().info("[DataBase] The player base is loaded.");
                this.postEnable();
                getWgRegionProtect().getRsApi().getLogger().info("[DataBase] Startup duration: {TIME} мс.".replace("{TIME}", String.valueOf(System.currentTimeMillis() - duration_time_start)));
            }
        }
    }

    protected void postEnable() {
        getServer().getScheduler().cancelTasks(this);
        if (this.getWgRegionProtect().getUtilConfig().intervalReload > 0) {
            getWgRegionProtect().getRsStorage().dbLogsSource.loadAsync();
            getWgRegionProtect().getRsApi().getLogger().info("[DataBase] The base is loaded asynchronously.");
        }
    }

    protected void loadMetrics() {
        int pluginId = 12975;
        new Metrics(this, pluginId);
    }

    private WorldGuardRegionProtect getWgRegionProtect() {
        return this.wgRegionProtect;
    }
}