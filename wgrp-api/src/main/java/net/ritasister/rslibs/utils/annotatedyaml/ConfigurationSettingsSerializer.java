package net.ritasister.rslibs.utils.annotatedyaml;

import java.util.Map;

public interface ConfigurationSettingsSerializer {

    int getIndent();

    String serialize(String key, Object object);

    String getLineBreak();

    Map<String, Object> getValues(boolean deep);

    Map<String, Object> getValues(Object section, boolean deep);

    boolean isConfigurationSection(String path);

    boolean isConfigurationSection(Object object);

    /**
     * Register serializable object
     *
     * @param clazz class that extends ConfigurationSerializable
     */
    void registerSerializable(Class clazz);
}
