package net.ritasister.wgrp.rslibs.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperBase;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public PlaceholderAPIExpansion(WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return wgrpPlugin.getWgrpPaperBase().getDescription().getVersion();
    }

    @Override
    public @NotNull String getAuthor() {
        return wgrpPlugin.getWgrpPaperBase().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return wgrpPlugin.getWgrpPaperBase().getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        return switch (params.toLowerCase()) {
            case "region_spying" -> // Indicates whether spy mode is enabled for the player
                    wgrpPlugin.getSpyLog().contains(player.getUniqueId()) ? "true" : "false";
            default -> null;
        };
    }

}
