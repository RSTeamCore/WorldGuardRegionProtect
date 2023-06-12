package net.ritasister.wgrp.rslibs.checker.mob;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Golem;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class GolemCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final @NotNull Entity entity) {
        Golem golem = (Golem) entity;
        EntityType golemType = golem.getType();
        return wgrpContainer.getConfig().getAnimalType().contains(golemType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[]{
                EntityType.SNOWMAN,
                EntityType.IRON_GOLEM
        };
    }
}
