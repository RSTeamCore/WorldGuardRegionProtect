package net.ritasister.wgrp.rslibs.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.ConfigFields;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class MinecartMaterialCheckTypeImpl implements EntityCheckType<Entity, EntityType> {

    private final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin;

    public MinecartMaterialCheckTypeImpl(final WorldGuardRegionProtectPaperPlugin wgrpBukkitPlugin) {
        this.wgrpBukkitPlugin = wgrpBukkitPlugin;
    }

    @Override
    public boolean check(final Entity entity) {
        final Minecart minecart = (Minecart) entity;
        final Material minecartMaterial = minecart.getMinecartMaterial();
        return ConfigFields.VEHICLE_TYPE.getList(wgrpBukkitPlugin.getWgrpPaperBase()).contains(minecartMaterial.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            EntityType.MINECART,
            EntityType.CHEST_MINECART,
            EntityType.TNT_MINECART,
            EntityType.HOPPER_MINECART,
            EntityType.SPAWNER_MINECART,
            EntityType.COMMAND_BLOCK_MINECART,
            EntityType.FURNACE_MINECART
        };
    }

}
