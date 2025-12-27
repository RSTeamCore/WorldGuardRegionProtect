package net.ritasister.wgrp.rslibs.api.checker.entity.transport;

import net.ritasister.wgrp.WorldGuardRegionProtectPaperPlugin;
import net.ritasister.wgrp.api.model.entity.EntityCheckType;
import net.ritasister.wgrp.util.file.config.field.ConfigFields;
import net.ritasister.wgrp.util.utility.entity.EntityHelper;
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
        return ConfigFields.VEHICLE_TYPE.asStringList(wgrpBukkitPlugin.getWgrpPaperBase()).contains(minecartMaterial.name().toLowerCase());
    }

    @Contract(value = " -> new", pure = true)
    @Override
    public EntityType @NotNull [] getEntityType() {
        return new EntityType[] {
            EntityType.MINECART,
            //Only available on older version like be 1.20.2
            EntityHelper.getEntityType("MINECART_CHEST"),
            EntityHelper.getEntityType("MINECART_COMMAND"),
            EntityHelper.getEntityType("MINECART_FURNACE"),
            EntityHelper.getEntityType("MINECART_HOPPER"),
            EntityHelper.getEntityType("MINECART_MOB_SPAWNER"),
            EntityHelper.getEntityType("MINECART_TNT"),
            //Only available on newest version like be 1.21+
            EntityHelper.getEntityType("CHEST_MINECART"),
            EntityHelper.getEntityType("TNT_MINECART"),
            EntityHelper.getEntityType("HOPPER_MINECART"),
            EntityHelper.getEntityType("SPAWNER_MINECART"),
            EntityHelper.getEntityType("COMMAND_BLOCK_MINECART"),
            EntityHelper.getEntityType("FURNACE_MINECART"),
        };
    }

}
