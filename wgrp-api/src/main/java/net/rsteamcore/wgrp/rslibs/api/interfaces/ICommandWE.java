package net.rsteamcore.wgrp.rslibs.api.interfaces;

import net.rsteamcore.wgrp.rslibs.util.wg.Iwg;

public interface ICommandWE {

    Iwg setUpWorldGuardVersionSeven();
    boolean cmdWE(String param);
    boolean cmdWE_C(String param);
    boolean cmdWE_P(String param);
    boolean cmdWE_S(String param);
    boolean cmdWE_U(String param);
    boolean cmdWE_CP(String param);
}
