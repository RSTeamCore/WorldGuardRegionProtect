package net.ritasister.wgrp.rslibs.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import net.ritasister.wgrp.rslibs.api.interfaces.CommandWE;
import net.ritasister.wgrp.rslibs.util.wg.WG;
import net.ritasister.wgrp.util.wg.WG7Impl;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CommandWEImpl implements CommandWE {

    private final WorldGuardRegionProtect wgRegionProtect;

    public WG setUpWorldGuardVersionSeven() {
        try {
            Class.forName("com.sk89q.worldedit.math.BlockVector3");
            return new WG7Impl(this.wgRegionProtect);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return this.wgRegionProtect.getWg();
    }

    public boolean cmdWE(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWe()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_C(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeC()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_P(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_S(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeS()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_U(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeU()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_CP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeCP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
