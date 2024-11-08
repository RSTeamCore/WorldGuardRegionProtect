package net.ritasister.wgrp.rslibs.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.config.ConfigFields;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;

public class MinecartMaterialCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public MinecartMaterialCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Minecart minecart = (Minecart) entity;
        final Material minecartMaterial = minecart.getMinecartMaterial();
        return ConfigFields.VEHICLE_TYPE.getList(wgrpBukkitPlugin.getWgrpBukkitBase()).contains(minecartMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
            EntityType.MINECART,
            EntityType.CHEST_MINECART,
            EntityType.TNT_MINECART,
            EntityType.HOPPER_MINECART,
            EntityType.SPAWNER_MINECART,
            EntityType.COMMAND_BLOCK_MINECART,
            EntityType.FURNACE_MINECART
        };
    }

}
