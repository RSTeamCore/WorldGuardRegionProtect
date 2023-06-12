package net.ritasister.wgrp.rslibs.checker.misc;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.rslibs.checker.EntityCheckType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HangingCheckTypeImpl implements EntityCheckType {

    private final WGRPContainer wgrpContainer;

    @Override
    public boolean check(final Entity entity) {
        Hanging hanging = (Hanging) entity;
        EntityType hangingType = hanging.getType();
        return wgrpContainer.getConfig().getInteractType().contains(hangingType.name().toLowerCase());
    }

    @Override
    public EntityType[] getEntityType() {
        return new EntityType[] {
                EntityType.PAINTING,
                EntityType.ITEM_FRAME,
                EntityType.GLOW_ITEM_FRAME
        };
    }
}
