package net.ritasister.rslibs.utils.annotatedyaml;

import java.io.File;

public interface ConfigurationProvider {

    /**
     * Configuration file
     *
     * @return Configuration file
     */
    File getConfigFile();

    /**
     * Get value from configuration
     * Can be used to get config version's
     *
     * @param path path
     * @param <T>  type
     * @return value or null
     */
    <T> T get(String path);

    /**
     * Set value to configuration
     * Can be used for migrations
     *
     * @param path
     * @param value
     */
    void set(String path, Object value);

    void reloadFileFromDisk();

    void setFile(File file);

    boolean isFileSuccessfullyLoaded();

    ConfigurationSettingsSerializer getConfigurationSettingsSerializer();
}
