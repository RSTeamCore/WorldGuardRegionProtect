package net.ritasister.wgrp.rslibs.api.location;

import net.ritasister.wgrp.api.model.location.Location;
import net.ritasister.wgrp.api.model.location.World;

import java.util.UUID;

public class WorldAdapter implements World {

    private final org.bukkit.World bukkitWorld;

    public WorldAdapter(org.bukkit.World world) {
        this.bukkitWorld = world;
    }

    @Override
    public UUID getUUID() {
        return bukkitWorld.getUID();
    }

    @Override
    public String getName() {
        return bukkitWorld.getName();
    }

    @Override
    public Environment getEnvironment() {
        return switch (bukkitWorld.getEnvironment()) {
            case NORMAL -> Environment.NORMAL;
            case NETHER -> Environment.NETHER;
            case THE_END -> Environment.END;
            default -> Environment.CUSTOM;
        };
    }

    @Override
    public Difficulty getDifficulty() {
        return switch (bukkitWorld.getDifficulty()) {
            case PEACEFUL -> Difficulty.PEACEFUL;
            case EASY -> Difficulty.EASY;
            case NORMAL -> Difficulty.NORMAL;
            case HARD -> Difficulty.HARD;
            default -> throw new IllegalArgumentException("Unknown difficulty: " + bukkitWorld.getDifficulty());
        };
    }

    @Override
    public Location getSpawnLocation() {
        return new net.ritasister.wgrp.rslibs.api.location.Location(bukkitWorld.getSpawnLocation());
    }

}
