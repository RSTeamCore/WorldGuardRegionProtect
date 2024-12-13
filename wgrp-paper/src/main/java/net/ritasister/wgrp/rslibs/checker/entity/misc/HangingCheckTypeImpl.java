package net.ritasister.wgrp.rslibs.checker.entity.misc;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class HangingCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin;

    public HangingCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Hanging hanging = (Hanging) entity;
        final EntityType hangingType = hanging.getType();
        return ConfigFields.INTERACT_TYPE.getList(wgrpBukkitPlugin.getWgrpPaperBase()).contains(hangingType.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            EntityType.PAINTING,
            EntityType.ITEM_FRAME,
            EntityType.GLOW_ITEM_FRAME
        };
    }

}
