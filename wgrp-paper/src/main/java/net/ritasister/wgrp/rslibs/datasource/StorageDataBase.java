package net.ritasister.wgrp.rslibs.datasource;

import java.util.UUID;

public class StorageDataBase {

    private final int id;
    private final String nickname;
    private final UUID uniqueId;
    private final long time;
    private final String action;
    private final String region;
    private final String world;
    private final double x;
    private final double y;
    private final double z;

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

    public String getLogAction() {
        return null;
    }
}
