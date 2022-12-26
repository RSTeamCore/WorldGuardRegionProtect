package net.rsteamcore.wgrp.rslibs.api;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import net.rsteamcore.wgrp.WorldGuardRegionProtect;
import net.rsteamcore.wgrp.rslibs.api.interfaces.ICommandWE;
import net.rsteamcore.wgrp.rslibs.util.wg.Iwg;
import net.rsteamcore.wgrp.util.wg.wg7;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class CommandWE implements ICommandWE {

    private final WorldGuardRegionProtect wgRegionProtect;

    public Iwg setUpWorldGuardVersionSeven() {
        try {
            Class.forName("com.sk89q.worldedit.math.BlockVector3");
                return new wg7(this.wgRegionProtect);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
        }
        return this.wgRegionProtect.getIwg();
    }

    public boolean cmdWE(String s) {
        s = s.replace("worldedit:", "");
        for(String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWe()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWE_C(String s) {
        s = s.replace("worldedit:", "");
        for(String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeC()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }return false;
    }

    public boolean cmdWE_P(String s) {
        s = s.replace("worldedit:", "");
        for(String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }return false;
    }

    public boolean cmdWE_S(String s) {
        s = s.replace("worldedit:", "");
        for(String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeS()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }return false;
    }

    public boolean cmdWE_U(String s) {
        s = s.replace("worldedit:", "");
        for(String tmp :wgRegionProtect.getUtilConfig().getConfig().getCmdWeU()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }return false;
    }

    public boolean cmdWE_CP(String s) {
        s = s.replace("worldedit:", "");
        for(String tmp : wgRegionProtect.getUtilConfig().getConfig().getCmdWeCP()) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }return false;
    }
}
