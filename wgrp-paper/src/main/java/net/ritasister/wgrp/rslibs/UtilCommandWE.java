package net.ritasister.wgrp.rslibs;

import net.ritasister.wgrp.api.CheckIntersection;
import org.bukkit.entity.Player;

public interface UtilCommandWE {

    CheckIntersection<Player> setUpWorldGuardVersionSeven();

    boolean cmdWe(String param);

    boolean cmdWe_C(String param);

    boolean cmdWe_P(String param);

    boolean cmdWe_S(String param);

    boolean cmdWe_U(String param);

    boolean cmdWe_CP(String param);
}
