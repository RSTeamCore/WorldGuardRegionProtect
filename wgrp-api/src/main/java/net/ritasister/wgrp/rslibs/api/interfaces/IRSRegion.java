package net.ritasister.wgrp.rslibs.api.interfaces;

import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public interface IRSRegion {

    boolean checkStandingRegion(@NotNull Location location, HashMap<String, List<String>> regions);
    boolean checkStandingRegion(@NotNull Location location);
}
