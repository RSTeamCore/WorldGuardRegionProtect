package net.ritasister.wgrp.rslibs.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.ritasister.wgrp.WorldGuardRegionProtectBukkitBase;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final WorldGuardRegionProtectBukkitBase wgrpBukkitBase;

    public PlaceholderAPIExpansion(WorldGuardRegionProtectBukkitBase wgrpBukkitBase) {
        this.wgrpBukkitBase = wgrpBukkitBase;
    }

    @Override
    public @NotNull String getIdentifier() {
        return wgrpBukkitBase.getDescription().getVersion();
    }

    @Override
    public @NotNull String getAuthor() {
        return wgrpBukkitBase.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return wgrpBukkitBase.getDescription().getVersion();
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
