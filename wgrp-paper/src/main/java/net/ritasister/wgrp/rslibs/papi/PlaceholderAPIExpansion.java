package net.ritasister.wgrp.rslibs.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
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
        return wgrpPlugin.getBootstrap().getLoader().getDescription().getVersion();
    }

    @Override
    public @NotNull String getAuthor() {
        return wgrpPlugin.getBootstrap().getLoader().getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return wgrpPlugin.getBootstrap().getLoader().getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (player == null || params.trim().isEmpty()) {
            return "Invalid request";
        }

        return switch (params.toLowerCase()) {
            case "region_spying" ->
                    wgrpPlugin.getSpyLog().contains(player.getUniqueId()) ? "true" : "false";
            case "protected_region_name" -> {
                if (player.getLocation() == null || player.getLocation().getWorld() == null) {
                    yield "Unknown world";
                }
                yield wgrpPlugin.getRegionAdapter().getProtectRegionName(player.getLocation());
            }
            default -> "Unknown placeholder";
        };
    }

}
