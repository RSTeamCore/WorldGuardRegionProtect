package net.ritasister.rslibs.datasource;

import net.ritasister.wgrp.WorldGuardRegionProtect;

import java.util.UUID;

public class StorageDataBase {

    private final int id;
    private final String nickname;
    private final UUID uniqueId;
    private final String time;
    private final String action;
    private final String region;
    private String world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public StorageDataBase(final int id, final String nickname, final UUID uniqueId, final String time, final String action, final String region, final String world, final Double x, final Double y, final Double z, final Float yaw, final Float pitch) {
        this.id=id;
        this.nickname=nickname;
        this.uniqueId=uniqueId;
        this.time=time;
        this.action=action;
        this.region=region;
        this.world=world;
        this.x=x;
        this.y=y;
        this.z=z;
        this.pitch=pitch;
        this.yaw=yaw;
    }
    public void setLogAction(String world,
                             double x,
                             double y,
                             double z,
                             float yaw,
                             float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        WorldGuardRegionProtect.dbLogsSource.logAction(
                this.nickname,
                this.uniqueId,
                this.time,
                this.action,
                this.region,
                this.world,
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch);
    }
}