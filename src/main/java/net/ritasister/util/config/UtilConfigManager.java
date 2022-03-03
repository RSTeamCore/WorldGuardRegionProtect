package net.ritasister.util.config;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.rslibs.utils.annotatedyaml.Configuration;
import net.ritasister.rslibs.utils.annotatedyaml.ConfigurationProvider;
import net.ritasister.rslibs.utils.annotatedyaml.provider.BukkitConfigurationProvider;
import net.ritasister.wgrp.WorldGuardRegionProtect;

public class UtilConfigManager {

    private UtilConfig utilConfig;
    private UtilConfigMessage utilConfigMessage;

    public void loadConfig() {
        //fixFolder();
        utilConfig = Configuration.builder(UtilConfig.class)
                .file(new File(WorldGuardRegionProtect.instance.getDataFolder(), "config.yml"))
                .provider(BukkitConfigurationProvider.class).build();
        ConfigurationProvider provider = utilConfig.getConfigurationProvider();
        provider.reloadFileFromDisk();
        File file = provider.getConfigFile();
        //rename old config
        if (file.exists() && provider.get("config-version") == null) {
            try {
                Files.move(file.toPath(), new File(file.getParentFile(), "config " + System.nanoTime() + ".yml").toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            provider.reloadFileFromDisk();
        }
        if (!file.exists()) {
            //create new file
            utilConfig.save();
            utilConfig.loaded();
            RSLogger.info("&2config.yml succesfull created!");
        } else if (provider.isFileSuccessfullyLoaded()) {
            if (utilConfig.load()) {
                if (!((String) provider.get("config-version")).equals(utilConfig.getConfigVersion())) {
                    RSLogger.info("&2The configuration has been updated. Check the new values");
                    utilConfig.save();
                }
                RSLogger.info("&2Default config loaded successfully");
            } else {
                RSLogger.warn("Failed to load config");
                utilConfig.loaded();
            }
        } else {
            RSLogger.warn("Can't load settings from file, using default...");
        }
    }


    public void loadMessageConfig() {
        //fixFolder();
        utilConfigMessage = Configuration.builder(UtilConfigMessage.class)
                .file(new File(WorldGuardRegionProtect.instance.getDataFolder(), "messages.yml"))
                .provider(BukkitConfigurationProvider.class).build();
        ConfigurationProvider provider = utilConfigMessage.getConfigurationProvider();
        provider.reloadFileFromDisk();
        File file = provider.getConfigFile();
        //rename old config
        if (file.exists() && provider.get("config-message-version") == null) {
            try {
                Files.move(file.toPath(), new File(file.getParentFile(), "messages " + System.nanoTime() + ".yml").toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
            provider.reloadFileFromDisk();
        }
        if (!file.exists()) {
            //create new file
            utilConfigMessage.save();
            utilConfigMessage.loaded();
            RSLogger.info("&2messages.yml succesfull created!");
        } else if (provider.isFileSuccessfullyLoaded()) {
            if (utilConfigMessage.load()) {
                if (!((String) provider.get("config-message-version")).equals(utilConfigMessage.getConfigVersion())) {
                    RSLogger.info("&2The configuration has been updated. Check the new values");
                    utilConfigMessage.save();
                }
                RSLogger.info("&2Message config loaded successfully");
            } else {
                RSLogger.warn("Failed to load config");
                utilConfigMessage.loaded();
            }
        } else {
            RSLogger.warn("Can't load settings from file, using default...");
        }
    }

    private void fixFolder() {
        File oldFolder = new File(WorldGuardRegionProtect.instance.getDataFolder().getParentFile(), "WorldGuardRegionProtect");
        if (!oldFolder.exists()) {
            return;
        }
        try {
            File actualFolder = oldFolder.getCanonicalFile();
            if (actualFolder.getName().equals("WorldGuardRegionProtect")) {
                File oldConfig = new File(actualFolder, "config.yml");
                if (!oldConfig.exists()) {
                    deleteFolder(actualFolder.toPath());
                    return;
                }
                //save old config
                List<String> oldConfigLines = Files.readAllLines(oldConfig.toPath(), StandardCharsets.UTF_8);
                String firstLine = oldConfigLines.size() > 0 ? oldConfigLines.get(0) : null;
                //delete old folder
                deleteFolder(actualFolder.toPath());

                //create new folder if needed
                File newFolder = WorldGuardRegionProtect.instance.getDataFolder();
                if (!newFolder.exists()) {
                    newFolder.mkdir();
                }
                File oldConfigInNewFolder = new File(newFolder, "config.yml");

                if (firstLine != null && firstLine.startsWith("config-version")) {
                    if (oldConfigInNewFolder.exists()) {
                        //save old config
                        Files.move(oldConfigInNewFolder.toPath(), new File(oldConfigInNewFolder.getParentFile(),
                                "config " + System.nanoTime() + ".yml").toPath());
                    }
                    //write old config from Antirelog folder to AntiRelog folder
                    Files.write(oldConfigInNewFolder.toPath(), oldConfigLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                    //RSLogger.info("Old config.yml file from folder 'WGRP' was moved to 'WorldGuardRegionProtect' folder");
                } else {
                    //Olny write old config to different file
                    Files.write(new File(oldConfigInNewFolder.getParentFile(), "config.old." + System.nanoTime()).toPath(),
                            oldConfigLines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
                    //getLogger(Level.WARNING, "Old config.yml file from folder 'WGRP' was moved to 'WorldGuardRegionProtect' folder with different name");
                }
            }
        } catch (IOException e) {
            RSLogger.warn("Something going wrong while renaming folder WGRP -> WorldGuardRegionProtect", e);
        }
    }

    private void deleteFolder(Path folder) throws IOException {
        try (Stream<Path> walk = Files.walk(folder)) {
            walk.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    public void reloadSettings() {
        utilConfig.getConfigurationProvider().reloadFileFromDisk();
        if (utilConfig.getConfigurationProvider().isFileSuccessfullyLoaded()) {
            utilConfig.load();
        }
    }
}