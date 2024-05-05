package net.ritasister.wgrp.core.api.entity.transport;

import net.ritasister.wgrp.core.WorldGuardRegionProtectPlugin;
import net.ritasister.wgrp.core.api.entity.EntityCheckType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;

public class MinecartMaterialCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectPlugin wgrpBukkitPlugin;

    public MinecartMaterialCheckTypeImpl(final WorldGuardRegionProtectPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        Minecart minecart = (Minecart) entity;
        Material minecartMaterial = minecart.getMinecartMaterial();
        return wgrpBukkitPlugin.getConfigLoader().getConfig().getVehicleType().contains(minecartMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.MINECART,
                EntityType.MINECART_CHEST,
                EntityType.MINECART_FURNACE,
                EntityType.MINECART_TNT,
                EntityType.MINECART_HOPPER,
                EntityType.MINECART_COMMAND,
                EntityType.MINECART_MOB_SPAWNER
        };
    }

}
