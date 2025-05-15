package net.ritasister.wgrp.rslibs.api;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.rslibs.UtilCommandWE;
import net.ritasister.wgrp.rslibs.wg.CheckIntersection;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.jetbrains.annotations.NotNull;

public class UtilWEImpl implements UtilCommandWE {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;

    public UtilWEImpl(final WorldGuardRegionProtectPaperPlugin wgrpPlugin) {
        this.wgrpPlugin = wgrpPlugin;
    }

    public CheckIntersection setUpWorldGuardVersionSeven() {
        return new CheckIntersection(this.wgrpPlugin);
    }

    public boolean cmdWe(@NotNull String s) {
        final String cleanedInput = s.replace("worldedit:", "").toLowerCase();
        return ConfigFields.CMD_WE.getList(wgrpPlugin.getWgrpPaperBase())
                .stream()
                .anyMatch(command -> command.equalsIgnoreCase(cleanedInput));
    }

    public boolean cmdWeC(@NotNull String s) {
        final String cleanedInput = s.replace("worldedit:", "").toLowerCase();
        return ConfigFields.CMD_WE_C.getList(wgrpPlugin.getWgrpPaperBase())
                .stream()
                .anyMatch(command -> command.equalsIgnoreCase(cleanedInput));
    }

    public boolean cmdWeP(@NotNull String s) {
        final String cleanedInput = s.replace("worldedit:", "").toLowerCase();
        return ConfigFields.CMD_WE_P.getList(wgrpPlugin.getWgrpPaperBase())
                .stream()
                .anyMatch(command -> command.equalsIgnoreCase(cleanedInput));
    }

    public boolean cmdWeS(@NotNull String s) {
        final String cleanedInput = s.replace("worldedit:", "").toLowerCase();
        return ConfigFields.CMD_WE_S.getList(wgrpPlugin.getWgrpPaperBase())
                .stream()
                .anyMatch(command -> command.equalsIgnoreCase(cleanedInput));
    }

    public boolean cmdWeU(@NotNull String s) {
        final String cleanedInput = s.replace("worldedit:", "").toLowerCase();
        return ConfigFields.CMD_WE_U.getList(wgrpPlugin.getWgrpPaperBase())
                .stream()
                .anyMatch(command -> command.equalsIgnoreCase(cleanedInput));
    }

    public boolean cmdWeCP(@NotNull String s) {
        final String cleanedInput = s.replace("worldedit:", "").toLowerCase();
        return ConfigFields.CMD_WE_CP.getList(wgrpPlugin.getWgrpPaperBase())
                .stream()
                .anyMatch(command -> command.equalsIgnoreCase(cleanedInput));
    }

}
