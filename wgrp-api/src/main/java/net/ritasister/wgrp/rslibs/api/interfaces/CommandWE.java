package net.ritasister.wgrp.rslibs.api.interfaces;

import net.ritasister.wgrp.rslibs.util.wg.WG;

public interface CommandWE {

    WG setUpWorldGuardVersionSeven();
    boolean cmdWE(String param);
    boolean cmdWE_C(String param);
    boolean cmdWE_P(String param);
    boolean cmdWE_S(String param);
    boolean cmdWE_U(String param);
    boolean cmdWE_CP(String param);
}
