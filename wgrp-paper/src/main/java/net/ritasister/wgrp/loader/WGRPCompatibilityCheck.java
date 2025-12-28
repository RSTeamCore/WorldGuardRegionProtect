package net.ritasister.wgrp.loader;

import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.util.utility.platform.PlatformDetector;
import net.ritasister.wgrp.util.utility.version.MinecraftVersionChecker;

public class WGRPCompatibilityCheck {

    private final MinecraftVersionChecker versionChecker;
    private final PlatformDetector platformDetector;
    private final PluginDisabler pluginDisabler;

    public WGRPCompatibilityCheck(MinecraftVersionChecker versionChecker,
                                  PlatformDetector platformDetector,
                                  PluginDisabler pluginDisabler) {
        this.versionChecker = versionChecker;
        this.platformDetector = platformDetector;
        this.pluginDisabler = pluginDisabler;
    }

    public boolean performCompatibilityChecks() {
        if (!versionChecker.check(pluginDisabler.plugin)) {
            pluginDisabler.disableWithMessage("Incompatible Minecraft version");
            return false;
        }

        final String platform = platformDetector.detectPlatform(pluginDisabler.plugin.getBootstrap(), pluginDisabler.plugin.getType());

        if (Platform.Type.UNKNOWN.getPlatformName().equals(platform)) {
            pluginDisabler.disableWithMessage("Unsupported server platform: " + platform);
            return false;
        }

        return true;
    }
}
