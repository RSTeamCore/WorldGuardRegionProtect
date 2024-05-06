package wgrp.rslibs.checker.entity.misc;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import wgrp.WorldGuardRegionProtectBukkitPlugin;
import wgrp.rslibs.checker.entity.EntityCheckType;

public class ArmorStandCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public ArmorStandCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
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
