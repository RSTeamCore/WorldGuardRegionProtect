package net.ritasister.wgrp.util.utility;

import org.jetbrains.annotations.NotNull;

public final class UtilityClass {

    private UtilityClass() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static boolean isClassPresent(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    public static boolean areClassesPresent(String @NotNull... classNames) {
        for (String className : classNames) {
            if (!isClassPresent(className)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAnyClassPresent(String @NotNull... classNames) {
        for (String className : classNames) {
            if (isClassPresent(className)) {
                return true;
            }
        }
        return false;
    }

    public static @NotNull String getMissingClasses(String @NotNull... classNames) {
        final StringBuilder missingClasses = new StringBuilder();
        for (String className : classNames) {
            if (!isClassPresent(className)) {
                if (!missingClasses.isEmpty()) {
                    missingClasses.append(", ");
                }
                missingClasses.append(className);
            }
        }
        return missingClasses.toString();
    }

}
