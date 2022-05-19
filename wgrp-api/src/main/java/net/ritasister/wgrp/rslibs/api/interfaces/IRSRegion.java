package net.ritasister.wgrp.rslibs.api.interfaces;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IRSRegion {

    boolean checkStandingRegion(@NotNull Location location, List<String> list);
    boolean checkStandingRegion(@NotNull Location location);
    boolean checkStandingRegion(World world, @NotNull Location location, List<String> list);
}
