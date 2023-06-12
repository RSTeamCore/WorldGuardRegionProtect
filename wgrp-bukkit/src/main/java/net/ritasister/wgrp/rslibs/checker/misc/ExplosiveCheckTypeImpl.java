package net.ritasister.wgrp.rslibs.checker.misc;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Explosive;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ExplosiveCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final @NotNull Entity entity) {
        Explosive explosive = (Explosive) entity;
        EntityType explosiveType = explosive.getType();
        return wgrpContainer.getConfig().getEntityExplodeType().contains(explosiveType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.PRIMED_TNT,
                EntityType.ENDER_CRYSTAL,
                EntityType.MINECART_TNT,
                EntityType.CREEPER,
                EntityType.WITHER_SKULL,
                EntityType.FIREBALL
        };
    }
}
