package net.ritasister.wgrp.rslibs.api.interfaces;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public interface RSRegion {

    boolean checkStandingRegion(@NotNull Location location);

    boolean checkStandingRegion(@NotNull Location location, Map<String, List<String>> regions);

    String getProtectRegionNameBySelection(Player player);

    String getProtectRegionName(Location location);

    void startActionBar();

    void sendActionBar(@NotNull Player player, Component message);

}
