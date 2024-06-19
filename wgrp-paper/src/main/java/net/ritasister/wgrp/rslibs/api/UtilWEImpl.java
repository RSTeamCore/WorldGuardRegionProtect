package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.wg.CheckIntersectionImpl;

public class UtilWEImpl implements UtilCommandWE {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private Config config;

    public UtilWEImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public CheckIntersection setUpWorldGuardVersionSeven() {
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
        return new CheckIntersectionImpl(this.wgrpBukkitPlugin);
    }

    public boolean cmdWe(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : this.config.getCmdWe()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase()))
                return true;
        }
        return false;
    }

    public boolean cmdWe_C(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeC()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWe_P(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWe_S(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeS()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWe_U(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeU()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWe_CP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeCP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
