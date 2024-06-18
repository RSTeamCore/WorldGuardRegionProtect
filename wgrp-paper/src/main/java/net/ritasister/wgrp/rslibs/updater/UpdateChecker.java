package net.ritasister.wgrp.rslibs.updater;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private final JavaPlugin plugin;
    private final int resourceId;

    public UpdateChecker(JavaPlugin plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    /**
     * Method checker if plugin has a last version.
     */
    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getAsyncScheduler().runNow(this.plugin, t -> {
            try (InputStream inputStream = URI.create("https://api.spigotmc.org/legacy/update.php?resource="
                    + this.resourceId).toURL().openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }

}
