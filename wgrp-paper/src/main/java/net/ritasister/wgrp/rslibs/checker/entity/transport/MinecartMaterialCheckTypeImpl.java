package net.ritasister.wgrp.rslibs.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectBukkitPlugin;
import net.ritasister.wgrp.rslibs.checker.entity.EntityCheckType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;

public class MinecartMaterialCheckTypeImpl implements EntityCheckType {

    private final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin;

    public MinecartMaterialCheckTypeImpl(final WorldGuardRegionProtectBukkitPlugin wgrpBukkitPlugin) {
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
