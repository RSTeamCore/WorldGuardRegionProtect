package net.ritasister.wgrp.rslibs.checker.transport;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MinecartMaterialCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final Entity entity) {
        Minecart minecart = (Minecart) entity;
        Material minecartMaterial = minecart.getMinecartMaterial();
        return wgrpContainer.getConfig().getVehicleType().contains(minecartMaterial.name().toLowerCase());
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
