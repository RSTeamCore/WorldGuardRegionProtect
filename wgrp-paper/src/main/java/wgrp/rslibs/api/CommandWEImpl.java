package wgrp.rslibs.api;

import com.sk89q.worldedit.entity.Player;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.wg.WG7Impl;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.CommandWE;

public class CommandWEImpl implements CommandWE {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private Config config;

    public CommandWEImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    public CheckIntersection<Player> setUpWorldGuardVersionSeven() {
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
        return new WG7Impl(this.wgrpBukkitPlugin);
    }

    public boolean cmdWE(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWe()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_C(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeC()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_P(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_S(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeS()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_U(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeU()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_CP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : config.getCmdWeCP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
