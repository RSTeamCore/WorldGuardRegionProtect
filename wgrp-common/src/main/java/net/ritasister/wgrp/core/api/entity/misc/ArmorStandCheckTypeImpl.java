package net.ritasister.wgrp.core.api.entity.misc;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class ArmorStandCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin wgrpBukkitPlugin;

    public ArmorStandCheckTypeImpl(final WorldGuardRegionProtectPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }


    @Override
    public boolean check(final @NotNull Entity entity) {
        ArmorStand armorStand = (ArmorStand) entity;
        EntityType armorStandType = armorStand.getType();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getInteractType().contains(armorStandType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
            EntityType.ARMOR_STAND
        };
    }
}
