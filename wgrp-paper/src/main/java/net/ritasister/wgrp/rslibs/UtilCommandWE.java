package net.ritasister.wgrp.rslibs;

import net.ritasister.wgrp.api.CheckIntersection;
import org.bukkit.entity.Player;

public interface UtilCommandWE {

    CheckIntersection<Player> setUpWorldGuardVersionSeven();

    boolean cmdWe(String param);

    boolean cmdWeC(String param);

    boolean cmdWeP(String param);

    boolean cmdWeS(String param);

    boolean cmdWeU(String param);

    boolean cmdWeCP(String param);
}
