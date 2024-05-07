package net.ritasister.wgrp.rslibs;

import net.ritasister.wgrp.api.CheckIntersection;
import org.bukkit.entity.Player;

public interface UtilCommandWE {

    CheckIntersection<Player> setUpWorldGuardVersionSeven();
    boolean cmdWE(String param);
    boolean cmdWE_C(String param);
    boolean cmdWE_P(String param);
    boolean cmdWE_S(String param);
    boolean cmdWE_U(String param);
    boolean cmdWE_CP(String param);
}
