package net.rsteamcore.wgrp.rslibs.api;

public class WorldGuardRegionProtectApi {

    /** Instance of the API */
    private static WorldGuardRegionProtectApi instance;

    public WorldGuardRegionProtectApi() {
        instance=this;
    }

    /**
     * Instance setter for internal use by the plugin only.
     *
     * @param   instance
     *          API instance
     */
    protected static void setInstance(WorldGuardRegionProtectApi instance) {
        WorldGuardRegionProtectApi.instance = instance;
    }

    /**
     * Returns API instance. If instance was not set by the plugin, throws
     * {@code IllegalStateException}. This is usually caused by shading the API
     * into own project, which is not allowed. Another option is calling the method
     * before plugin was able to load.
     *
     * @return  API instance
     * @throws  IllegalStateException
     *          If instance is {@code null}
     */
    public static WorldGuardRegionProtectApi getInstance() {
        if (instance == null)
            throw new IllegalStateException("API instance is null. This likely means you shaded WGRP's API into your project" +
                    " instead of only using it, which is not allowed.");
        return instance;
    }
}
