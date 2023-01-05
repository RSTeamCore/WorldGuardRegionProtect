package net.ritasister.wgrp.rslibs.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final WorldGuardRegionProtect wgRegionProtect;

    public PlaceholderAPIExpansion(WorldGuardRegionProtect wgRegionProtect) {
        this.wgRegionProtect = wgRegionProtect;
    }

    @Override
    public @NotNull String getIdentifier() {
        return wgRegionProtect.getPluginVersion();
    }

    @Override
    public @NotNull String getAuthor() {
        return wgRegionProtect.getPluginAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return wgRegionProtect.getPluginVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        if (identifier.equals("playername")) {
            return p.getName();
        }
        if (identifier.equals("placeholder2")) {
            return "placeholder2 works";
        }
        return null;
    }

}
