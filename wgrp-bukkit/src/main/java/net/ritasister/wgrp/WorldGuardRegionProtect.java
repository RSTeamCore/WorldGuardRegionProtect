package net.ritasister.wgrp;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.loader.InitializationImpl;
import net.ritasister.wgrp.loader.LoaderCommandsAndListenersImpl;
import net.ritasister.wgrp.loader.WGRPInitialization;
import net.ritasister.wgrp.loader.WGRPLoaderCommandsAndListeners;
import net.ritasister.wgrp.util.config.loader.ConfigLoader;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class WorldGuardRegionProtect {

    private final WGRPBukkitPlugin wgrpBukkitPlugin;

    @Getter
    private WGRPContainer wgrpContainer;

    public void load() {
        this.wgrpContainer = new WGRPContainer(this);

        InitializationImpl<WorldGuardRegionProtect> wgrpInitialization = new WGRPInitialization();
        wgrpInitialization.wgrpInit(this);

        LoaderCommandsAndListenersImpl<WorldGuardRegionProtect> loader = new WGRPLoaderCommandsAndListeners();
        loader.loadCommands(this);
        loader.loadListeners(this);
    }

    public void unLoad() {
        ConfigLoader configLoader = getWgrpContainer().getConfigLoader();
        if (configLoader != null) {
            try {
                configLoader.getConfig().saveConfig();
            } catch (NullPointerException ignored) {
                WGRPContainer.getLogger().info("Cannot save config, because config is not loaded!");
            }
        }
    }

    public WGRPBukkitPlugin getWGRPBukkitPlugin() {
        return this.wgrpBukkitPlugin;
    }

}
