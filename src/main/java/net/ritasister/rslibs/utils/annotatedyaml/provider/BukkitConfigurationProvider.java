package net.ritasister.rslibs.utils.annotatedyaml.provider;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import net.ritasister.rslibs.utils.annotatedyaml.ConfigurationProvider;
import net.ritasister.rslibs.utils.annotatedyaml.ConfigurationSettingsSerializer;
import net.ritasister.rslibs.utils.annotatedyaml.ConfigurationUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;

public class BukkitConfigurationProvider implements ConfigurationProvider {

    private File file;
    private BukkitConfigurationSettingsSerializer serializer;
    private YamlConfiguration yamlConfiguration;
    private boolean fileLoadedWithoutErrors;

    public BukkitConfigurationProvider() {

    }

    public BukkitConfigurationProvider(File file) {
        this.file = file;
    }

    @Override
    public File getConfigFile() {
        return file;
    }

    @Override
    public void reloadFileFromDisk() {
        this.yamlConfiguration = new YamlConfiguration();
        try {
            this.fileLoadedWithoutErrors = false;
            this.yamlConfiguration.load(file);
            this.fileLoadedWithoutErrors = true;
        } catch (FileNotFoundException fnf) {

        } catch (IOException | InvalidConfigurationException e) {
            ConfigurationUtils.LOGGER.log(Level.SEVERE, "Cannot load " + file, e);
        }
        if (this.serializer != null) {
            this.serializer.setYamlConfiguration(yamlConfiguration);
        } else {
            this.serializer = new BukkitConfigurationSettingsSerializer(yamlConfiguration);
        }
    }

    public YamlConfiguration getYamlConfiguration() {
        if (yamlConfiguration == null) {
            reloadFileFromDisk();
        }
        return yamlConfiguration;
    }

    @Override
    public boolean isFileSuccessfullyLoaded() {
        return fileLoadedWithoutErrors;
    }

    @Override
    public <T> T get(String path) {
        Object value = (yamlConfiguration.isList(path) ? yamlConfiguration.getList(path) : yamlConfiguration.get(path));
        return value == null ? null : (T) value;
    }

    @Override
    public void set(String path, Object value) {
        yamlConfiguration.set(path, value);
    }

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public ConfigurationSettingsSerializer getConfigurationSettingsSerializer() {
        return serializer;
    }
}
