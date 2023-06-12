package net.ritasister.wgrp.rslibs.checker.misc;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ArmorStandCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final @NotNull Entity entity) {
        ArmorStand armorStand = (ArmorStand) entity;
        EntityType armorStandType = armorStand.getType();
        return wgrpContainer.getConfig().getInteractType().contains(armorStandType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
            EntityType.ARMOR_STAND
        };
    }
}
