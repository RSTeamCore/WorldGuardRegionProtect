package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.CheckIntersection;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.util.config.Config;
import net.ritasister.wgrp.util.config.ConfigFields;
import net.ritasister.wgrp.util.wg.CheckIntersectionImpl;
import org.bukkit.entity.Player;

public class UtilWEImpl implements UtilCommandWE {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    private Config config;

    public UtilWEImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public CheckIntersection<Player> setUpWorldGuardVersionSeven() {
        this.config = wgrpBukkitPlugin.getConfigLoader().getConfig();
        return new CheckIntersectionImpl(this.wgrpBukkitPlugin);
    }

    public boolean cmdWe(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE.getList(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase()))
                return true;
        }
        return false;
    }

    public boolean cmdWeC(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_C.getList(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_P.getList(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeS(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_S.getList(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeU(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_U.getList(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean cmdWeCP(String s) {
        s = s.replace("worldedit:", "");
        for (String tmp : ConfigFields.CMD_WE_CP.getList(wgrpBukkitPlugin.getWgrpBukkitBase())) {
            if (tmp.equalsIgnoreCase(s.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}
