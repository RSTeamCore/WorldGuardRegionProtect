package net.ritasister.wgrp.rslibs.checker;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MinecartMaterialCheckTypeImpl implements EntityCheckType {

    private final Config config;

    @Override
    public boolean check(final Entity entity) {
        Minecart minecart = (Minecart) entity;
        Material minecartMaterial = minecart.getMinecartMaterial();
        return config.getVehicleType().contains(minecartMaterial.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.MINECART,
                EntityType.MINECART_CHEST,
                EntityType.MINECART_FURNACE,
                EntityType.MINECART_TNT,
                EntityType.MINECART_HOPPER};
    }

}
