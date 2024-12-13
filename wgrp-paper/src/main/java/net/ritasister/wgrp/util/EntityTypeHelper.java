package net.ritasister.wgrp.util;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public final class EntityTypeHelper {

    public static @Nullable EntityType getEntityType(String typeName) {
        try {
            return EntityType.valueOf(typeName);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

}
