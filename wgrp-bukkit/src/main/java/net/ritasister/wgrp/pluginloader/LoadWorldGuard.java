package net.ritasister.wgrp.pluginloader;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class LoadWorldGuard extends AbstractLoadLibs {

    private final WorldGuardRegionProtect wgRegionProtect;

    @Override
    public void loadPlugin() {
        final Plugin plugin = wgRegionProtect.getWGRPBukkitPlugin().getServer().getPluginManager().getPlugin("WorldGuard");
        if (plugin != null && plugin.isEnabled()) {
            try {
                wgRegionProtect.getLogger().info(String.format("Plugin: %s loaded successful!.", plugin.getName()));
            } catch (NullPointerException | ClassCastException | NoClassDefFoundError exception) {
                wgRegionProtect.getLogger().error(exception.getMessage());
            }
        }
    }

}
