package net.ritasister.wgrp.util.utility.entity;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public final class EntityHelper {

    private EntityHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static @NotNull EntityType getEntityType(String typeName) {
        try {
            return EntityType.valueOf(typeName);
        } catch (IllegalArgumentException ignored) {
            return EntityType.UNKNOWN;
        }
    }

}
