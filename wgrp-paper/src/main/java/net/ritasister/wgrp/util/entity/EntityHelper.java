package net.ritasister.wgrp.util.entity;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public final class EntityHelper {

    private EntityHelper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static @Nullable EntityType getEntityType(String typeName) {
        try {
            return EntityType.valueOf(typeName);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }
    

}
