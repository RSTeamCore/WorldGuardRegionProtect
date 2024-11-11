package net.ritasister.wgrp.rslibs.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.ritasister.wgrp.WorldGuardRegionProtectPaperBase;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final WorldGuardRegionProtectPaperBase wgrpPaperBase;

    public PlaceholderAPIExpansion(WorldGuardRegionProtectPaperBase wgrpPaperBase) {
        this.wgrpPaperBase = wgrpPaperBase;
    }

    @Override
    public @NotNull String getIdentifier() {
        return wgrpPaperBase.getDescription().getVersion();
    }

    @Override
    public @NotNull String getAuthor() {
        return wgrpPaperBase.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return wgrpPaperBase.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if (params.equalsIgnoreCase("placeholder1")) {
            return wgrpPaperBase.getConfig().getString("placeholders.placeholder1", "default1"); //
        }

        return null; //
    }

}
