package net.ritasister.wgrp.rslibs.updater;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.platform.Platform;
import net.ritasister.wgrp.util.schedulers.FoliaRunnable;
import net.ritasister.wgrp.util.utility.platform.PlatformDetector;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class UpdateChecker {

    private final WorldGuardRegionProtectPaperPlugin wgrpPlugin;
    private final int resourceId;

    public UpdateChecker(WorldGuardRegionProtectPaperPlugin wgrpPlugin, int resourceId) {
        this.wgrpPlugin = wgrpPlugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        final String platformName = PlatformDetector.getPlatformName();

        if (platformName.equals(Platform.Type.BUKKIT.getPlatformName())
                || platformName.equals(Platform.Type.SPIGOT.getPlatformName())) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this.wgrpPlugin.getWgrpPaperBase(), t ->
                    checkUpdate(consumer, platformName), 0, 6 * 60 * 60 * 20);
        } else if (platformName.equals(Platform.Type.PAPER.getPlatformName())) {
            Bukkit.getAsyncScheduler().runAtFixedRate(this.wgrpPlugin.getWgrpPaperBase(), t ->
                    checkUpdate(consumer, Platform.Type.PAPER.getPlatformName()), 0, 6, TimeUnit.HOURS);
        } else if (platformName.equals(Platform.Type.FOLIA.getPlatformName())) {
            new FoliaRunnable(Bukkit.getAsyncScheduler(), TimeUnit.HOURS) {
                @Override
                public void run() {
                    checkUpdate(consumer, Platform.Type.FOLIA.getPlatformName());
                }
            }.runAtFixedRate(wgrpPlugin.getWgrpPaperBase(), 0, 6);
        }
    }

    private void checkUpdate(final Consumer<String> consumer, String platformName) {
        wgrpPlugin.getLogger().info(String.format("Checking for updates using %s schedulers.", platformName));
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
             Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
            this.wgrpPlugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
        }
    }

}
