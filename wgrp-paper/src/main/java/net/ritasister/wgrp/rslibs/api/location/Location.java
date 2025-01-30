package net.ritasister.wgrp.rslibs.api.location;

import net.ritasister.wgrp.api.model.location.World;

public class Location implements net.ritasister.wgrp.api.model.location.Location {

    private final org.bukkit.Location bukkitLocation;

    public Location(org.bukkit.Location location) {
        this.bukkitLocation = location;
    }

    @Override
    public double getX() {
        return bukkitLocation.getX();
    }

    @Override
    public double getY() {
        return bukkitLocation.getY();
    }

    @Override
    public double getZ() {
        return bukkitLocation.getZ();
    }

    @Override
    public float getYaw() {
        return bukkitLocation.getYaw();
    }

    @Override
    public float getPitch() {
        return bukkitLocation.getPitch();
    }

    @Override
    public World getWorld() {
        return new WorldAdapter(bukkitLocation.getWorld());  // Адаптер для мира
    }

    @Override
    public String getName() {
        // Если у твоего мира есть свойство или метод для имени, например:
        return bukkitLocation.getWorld().getName() + " (" + getX() + ", " + getY() + ", " + getZ() + ")";
    }

    @Override
    public Location add(double x, double y, double z) {
        org.bukkit.Location newLocation = bukkitLocation.clone().add(x, y, z);
        return new Location(newLocation);
    }

    @Override
    public Location subtract(double x, double y, double z) {
        org.bukkit.Location newLocation = bukkitLocation.clone().subtract(x, y, z);
        return new Location(newLocation);
    }

    @Override
    public Location multiply(double multiplier) {
        org.bukkit.Location newLocation = new org.bukkit.Location(bukkitLocation.getWorld(),
                bukkitLocation.getX() * multiplier,
                bukkitLocation.getY() * multiplier,
                bukkitLocation.getZ() * multiplier);
        return new Location(newLocation);
    }

    @Override
    public Location divide(double divisor) {
        org.bukkit.Location newLocation = new org.bukkit.Location(bukkitLocation.getWorld(),
                bukkitLocation.getX() / divisor,
                bukkitLocation.getY() / divisor,
                bukkitLocation.getZ() / divisor);
        return new Location(newLocation);
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                ", world=" + getWorld() +
                '}';
    }

    @Override
    public Location clone() {
        return new Location(bukkitLocation.clone());
    }
}