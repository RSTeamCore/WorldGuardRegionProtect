package net.ritasister.wgrp.rslibs.checker;

import net.ritasister.wgrp.WGRPContainer;
import net.ritasister.wgrp.util.config.Config;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityCheckTypeProvider {

    private final List<EntityCheckType> entityCheckTypes;

    public EntityCheckTypeProvider(final @NotNull WGRPContainer wgrpContainer) {
        Config config = wgrpContainer.getConfig();

        this.entityCheckTypes = new ArrayList<>() {{
            add(new BoatMaterialCheckTypeImpl(config));
            add(new MinecartMaterialCheckTypeImpl(config));
        }};
    }

    public EntityCheckType getCheck(Entity entity) {
        return this.entityCheckTypes
                .stream()
                .filter((check) -> Arrays.asList(check.getEntityType()).contains(entity.getType()))
                .findFirst()
                .orElseThrow();
    }

}
