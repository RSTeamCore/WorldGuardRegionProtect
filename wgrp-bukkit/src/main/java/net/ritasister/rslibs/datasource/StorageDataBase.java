package net.ritasister.rslibs.datasource;

import net.ritasister.wgrp.WorldGuardRegionProtect;

import java.util.UUID;

public class StorageDataBase {

    private int id;
    private String nickname;
    private UUID uniqueId;
    private long time;
    private String action;
    private String region;
    private String world;
    private double x;
    private double y;
    private double z;

    public StorageDataBase(final int id, final String nickname, final UUID uniqueId, final long time, final String action, final String region, final String world, final Double x, final Double y, final Double z) {
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
    }
    public void setLogAction(String nickname, UUID uniqueId, long time, String action, String region, String world,
                             double x,
                             double y,
                             double z) {
        this.nickname=nickname;
        this.uniqueId=uniqueId;
        this.time=time;
        this.action=action;
        this.region=region;
        this.world=world;
        this.x=x;
        this.y=y;
        this.z=z;
        WorldGuardRegionProtect.dbLogsSource.logAction(
                this.nickname,
                this.uniqueId,
                this.time,
                this.action,
                this.region,
                this.world,
                this.x,
                this.y,
                this.z);
    }
    public String getLogAction() {
        return null;
    }
}