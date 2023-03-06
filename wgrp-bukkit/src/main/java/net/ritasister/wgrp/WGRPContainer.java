package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.papermc.paper.plugin.configuration.PluginMeta;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.rslibs.api.RSApi;
import net.ritasister.wgrp.rslibs.api.RSStorage;
import net.ritasister.wgrp.rslibs.api.interfaces.CommandWE;
import net.ritasister.wgrp.rslibs.api.interfaces.RSRegion;
import net.ritasister.wgrp.rslibs.util.updater.UpdateNotify;
import net.ritasister.wgrp.rslibs.util.wg.WG;
import net.ritasister.wgrp.util.ConfigLoader;
import net.ritasister.wgrp.util.MessageLoader;
import net.ritasister.wgrp.util.config.Config;
import net.rsteamcore.config.Container;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WGRPContainer {

    private final WorldGuardRegionProtect worldGuardRegionProtect;

    public WG wg;

    //DataBase
    public RSStorage rsStorage;

    //Api for this plugin
    public RSApi rsApi;
    public RSRegion rsRegion;

    public UpdateNotify updateNotify;

    //Configs
    public ConfigLoader configLoader;

    //Parameters for to intercept commands from WE or FAWE.
    public CommandWE commandWE;

    //List admin for who spy for another player.
    private final List<UUID> spyLog = new ArrayList<>();

    public RSApi getRsApi() {
        return rsApi;
    }

    public RSRegion getRsRegion() {
        return rsRegion;
    }

    public RSStorage getRsStorage() {
        return rsStorage;
    }

    public ConfigLoader getConfigLoader() {
        return configLoader;
    }

    public Container getMessages() {
        return getConfigLoader().getMessages();
    }

    public Config getConfig() {
        return getConfigLoader().getConfig();
    }

    public CommandWE getCommandWE() {
        return commandWE;
    }

    public PluginManager getPluginManager() {
        return Bukkit.getServer().getPluginManager();
    }

    public PluginMeta getPluginMeta() {
        return worldGuardRegionProtect.getWGRPBukkitPlugin().getPluginMeta();
    }

    public String getPluginVersion() {
        return getPluginMeta().getVersion();
    }

    public List<String> getPluginAuthors() {
        return getPluginMeta().getAuthors();
    }

    public WG getWg() {
        return wg;
    }

    public UpdateNotify getUpdateNotify() {
        return updateNotify;
    }

    public List<UUID> getSpyLog() {
        return spyLog;
    }

}
