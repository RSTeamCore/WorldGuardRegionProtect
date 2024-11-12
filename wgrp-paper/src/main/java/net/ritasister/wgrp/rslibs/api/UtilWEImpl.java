package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import net.ritasister.wgrp.util.wg.CheckIntersection;

public class UtilWEImpl implements UtilCommandWE {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public UtilWEImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    public CheckIntersection setUpWorldGuardVersionSeven() {
        return new CheckIntersection(this.wgrpPlugin);
    }

    public boolean cmdWe(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE.getList(wgrpPlugin.getWgrpPaperBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase()))
                return true;
        }
        return false;
    }

    public boolean cmdWeC(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_C.getList(wgrpPlugin.getWgrpPaperBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_P.getList(wgrpPlugin.getWgrpPaperBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeS(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_S.getList(wgrpPlugin.getWgrpPaperBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeU(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_U.getList(wgrpPlugin.getWgrpPaperBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeCP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_CP.getList(wgrpPlugin.getWgrpPaperBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
