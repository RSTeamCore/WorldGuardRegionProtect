package net.ritasister.wgrp.core.api.entity.misc;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

public class HangingCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin wgrpBukkitPlugin;

    public HangingCheckTypeImpl(final WorldGuardRegionProtectPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        Hanging hanging = (Hanging) entity;
        EntityType hangingType = hanging.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getInteractType().contains(hangingType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.PAINTING,
                EntityType.ITEM_FRAME,
                EntityType.GLOW_ITEM_FRAME
        };
    }
}
