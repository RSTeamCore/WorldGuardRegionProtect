package net.ritasister.util;

import java.io.File;
import java.io.IOException;

import net.ritasister.rslibs.api.RSLogger;
import net.ritasister.wgrp.WorldGuardRegionProtect;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Configs {

    CONFIG("config"),
    MESSAGES("messages");

    private String fileName;
    private File file;
    private String path = "";

    Configs(String fileName) {
        this.setFileName(fileName);
    }

    Configs(String fileName, String path) {
        this.setFileName(fileName);
        this.setPath(path);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return WorldGuardRegionProtect.instance.getDataFolder() + File.separator + path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        try {
            WorldGuardRegionProtect.instance.getConfig().save(file);
        } catch (IOException e) {
            RSLogger.warn("Could not save " + e.getMessage());
        }
    }

    public void createFile() {
        if (!WorldGuardRegionProtect.instance.getDataFolder().exists()) {
            WorldGuardRegionProtect.instance.getDataFolder().mkdirs();
            file = new File(getPath(), fileName + ".yml");
            if(!file.exists()) {
                WorldGuardRegionProtect.instance.saveResource(fileName + ".yml", true);
                RSLogger.LoadConfigMsgSuccess(fileName + ".yml");
                try{
				    file.createNewFile();
			    }catch(Exception e){
                    RSLogger.err("Could not create " + e.getMessage());
        	    }
            }
            FileConfiguration config = new YamlConfiguration();
            try{
                config.load(file);
            }catch(IOException | InvalidConfigurationException e) {
                RSLogger.warn("Could not load " + e.getMessage());
            }
        }
    }
}