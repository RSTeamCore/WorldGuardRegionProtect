package net.ritasister.wgrp.rslibs.api.model;

import net.ritasister.wgrp.api.model.entity.player.Player;
import net.ritasister.wgrp.api.model.location.Location;
import net.ritasister.wgrp.api.model.location.World;
import net.ritasister.wgrp.rslibs.api.location.WorldAdapter;

public class PlayerAdapter implements Player {

    private final org.bukkit.entity.Player bukkitPlayer;

    public PlayerAdapter(org.bukkit.entity.Player player) {
        this.bukkitPlayer = player;
    }

    @Override
    public String getUniqueId() {
        return bukkitPlayer.getUniqueId().toString();
    }

    @Override
    public EntityType getType() {
        return EntityType.PLAYER;
    }

    @Override
    public Location getLocation() {
        return null;
    }

    @Override
    public void moveTo(Location location) {
        bukkitPlayer.getLocation();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public String getName() {
        return bukkitPlayer.getName();
    }

    @Override
    public World getWorld() {
        return new WorldAdapter(bukkitPlayer.getWorld());
    }

    @Override
    public boolean hasPermission(String permission) {
        return bukkitPlayer.hasPermission(permission);
    }

    @Override
    public boolean isOnline() {
        return bukkitPlayer.isOnline();
    }
}