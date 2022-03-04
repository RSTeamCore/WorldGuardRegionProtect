package net.ritasister.rslibs.utils.annotatedyaml;

import net.ritasister.rslibs.utils.annotatedyaml.util.Validate;
import net.ritasister.rslibs.utils.annotatedyaml.Annotations.Key;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationUtils {

    public static Logger LOGGER = Logger.getLogger("AnnotatedYaml");


    public static void save(Configuration configuration, ConfigurationProvider provider) {
        Validate.notNull(configuration, "configuration");
        Validate.notNull(provider, "provider");
        Validate.notNull(provider.getConfigFile(), "provider.getConfigFile()");

        List<String> outList = new ArrayList<>();
        dump(configuration, provider.getConfigurationSettingsSerializer(), outList, 0);
        try {
            File out = provider.getConfigFile();
            net.ritasister.rslibs.utils.annotatedyaml.util.Files.createParentDirs(out);
            File tmpfile = new File(out.getParentFile(), "___tmpconfig");
            Files.write(tmpfile.toPath(), outList, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            try {
                Files.move(tmpfile.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tmpfile.toPath(), out.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Cannot save file ", e);
        }
    }

    private static List<String> dump(ConfigurationSection configuration, ConfigurationSettingsSerializer serializer, List<String> out,
                                     int currIndent) {
        try {
            String indent = repeat(" ", currIndent);

            Annotations.Comment comments = configuration.getClass().getAnnotation(Annotations.Comment.class);
            if (comments != null) {
                processComment(comments, indent, out);
            }

            for (Field field : configuration.getClass().getDeclaredFields()) {
                if (field.getAnnotation(Annotations.Ignore.class) != null) {
                    continue;
                }
                field.setAccessible(true);

                Key configKey = field.getAnnotation(Key.class);
                String key = configKey == null ? field.getName() : configKey.value();

                comments = field.getAnnotation(Annotations.Comment.class);
                if (comments != null) {
                    processComment(comments, indent, out);
                }

                Object value = field.get(configuration);
                if (value instanceof ConfigurationSection) {
                    out.add("");
                    out.add(indent + key + ":");
                    dump((ConfigurationSection) value, serializer, out, currIndent + serializer.getIndent());
                } else {
                    out.addAll(Arrays.asList(upgradeIndent(serializer.serialize(key, value), serializer.getLineBreak(), indent)));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not dump config", e);
        }
        return out;
    }

    private static void processComment(Annotations.Comment comment, String indent, List<String> out) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] comments = comment.value();
        if (comment.enumClass() != Object.class) {
            Enum en = Enum.valueOf((Class<Enum>) comment.enumClass(), (String) comment.value()[0]);
            comments = (String[]) en.getClass().getMethod("getComment").invoke(en);
        }
        for (String cmnt : comments) {
            out.add(indent + (cmnt.startsWith("#") ? cmnt : "#" + cmnt));
        }
    }

    public static void load(Configuration configuration, ConfigurationProvider provider) {
        Validate.notNull(configuration, "configuration");
        Validate.notNull(provider, "provider");
        loadRecursive(configuration, provider, "");
    }

    private static void loadRecursive(ConfigurationSection configuration, ConfigurationProvider provider, String currPath) {
        ConfigurationSettingsSerializer serializer = provider.getConfigurationSettingsSerializer();
        for (Field field : configuration.getClass().getDeclaredFields()) {
            try {
                if (field.getAnnotation(Annotations.Ignore.class) != null || field.getAnnotation(Annotations.Final.class) != null) {
                    continue;
                }
                field.setAccessible(true);
                Class<?> clazz = field.getType();
                Key key = field.getAnnotation(Key.class);
                String path = (currPath.isEmpty() ? "" : currPath + ".") + (key == null ? field.getName() : key.value());
                Object value = provider.get(path);
                if (serializer.isConfigurationSection(path) && ConfigurationSection.class.isAssignableFrom(clazz)) {
                    loadRecursive((ConfigurationSection) field.get(configuration), provider, path);
                    continue;
                }
                if (serializer.isConfigurationSection(path) && Map.class.isAssignableFrom(clazz)) {
                    value = transformMemorySectionToMap(serializer.getValues(value, true), serializer);
                } else if (clazz.isEnum() && value instanceof String) {
                    value = tryGetEnum(clazz, (String) value, false);
                } else if (clazz.isArray() && value instanceof List) {
                    Class<?> clazz1 = field.getType().getComponentType();
                    List list = (List) value;
                    value = Array.newInstance(clazz1, list.size());
                    Object[] arr = (Object[]) value;
                    for (int i = 0; i < list.size(); i++) {
                        arr[i] = clazz.cast(list.get(i));
                    }
                }

                if (value == null) {
                    LOGGER.log(Level.WARNING, "Can't set value to '" + field + " (" + clazz.getSimpleName() + ")', because " +
                            "'" + path + "' is not set or null");
                    continue;
                }

                if (clazz.isInstance(value)) {
                    setFieldValue(configuration, field, value);
                } else {
                    try {
                        setFieldValue(configuration, field, value);
                    } catch (Exception e) {
                        LOGGER.log(Level.WARNING,
                                "Can't set '" + value + " (" + value.getClass().getSimpleName() + ")' to '"
                                        + field + " (" + clazz.getSimpleName() + ")' for path '" + path + "': " + e.getMessage());

                    }

                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING,
                        "Can't set value to '" + field + " (" + field.getType().getSimpleName() + ")': " + e.getMessage());
            }
        }
        configuration.loaded();
    }

    private static Map<String, Object> transformMemorySectionToMap(Map<String, Object> map, ConfigurationSettingsSerializer serializer) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (serializer.isConfigurationSection(entry.getValue())) {
                entry.setValue(serializer.getValues(entry.getValue(), true));
                transformMemorySectionToMap((Map<String, Object>) entry.getValue(), serializer);
            }
        }
        return map;
    }


    private static Object tryGetEnum(Class clazz, String enumField, boolean fail) {
        try {
            return Enum.valueOf((Class<Enum>) clazz, enumField);
        } catch (Exception e) {
            if (fail) {
                throw e;
            }
            return tryGetEnum(clazz, enumField.toUpperCase(), true);
        }
    }

    private static <T> void setFieldValue(final Object object, final Field field, final T value) throws NoSuchFieldException, IllegalAccessException {
        Field modifiersField;
        boolean isModifiersAccessible;
        try {
            modifiersField = Field.class.getDeclaredField("modifiers");
            isModifiersAccessible = modifiersField.isAccessible();
            modifiersField.setAccessible(true);
        } catch (final NoSuchFieldException e) {
            // Java 11+ does not allow to access modifiers
            modifiersField = null;
            isModifiersAccessible = false;
        }

        final boolean isFieldAccessible = field.isAccessible();
        field.setAccessible(true);

        if (modifiersField != null) {
            final int modifiers = field.getModifiers();
            modifiersField.setInt(field, modifiers & ~Modifier.FINAL);
            field.set(object, value);
            modifiersField.setInt(field, modifiers);
        } else {
            field.set(object, value);
        }

        field.setAccessible(isFieldAccessible);
        if (modifiersField != null) {
            modifiersField.setAccessible(isModifiersAccessible);
        }
    }


    private static String repeat(final String s, final int n) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    private static String[] upgradeIndent(String original, String lineBreak, String indent) {
        String[] lines = original.split(lineBreak);
        for (int i = 0; i < lines.length; i++) {
            lines[i] = indent + lines[i];
        }
        return lines;
    }
}