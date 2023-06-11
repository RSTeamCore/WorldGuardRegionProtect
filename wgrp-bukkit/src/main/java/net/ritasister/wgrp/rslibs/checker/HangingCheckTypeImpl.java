package net.ritasister.wgrp.rslibs.checker;

import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Hanging;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class HangingCheckTypeImpl implements EntityCheckType {

    private final Config config;

    @Override
    public boolean check(final Entity entity) {
        Hanging hanging = (Hanging) entity;
        EntityType hangingType = hanging.getType();
        return config.getInteractType().contains(hangingType.name().toLowerCase());
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
