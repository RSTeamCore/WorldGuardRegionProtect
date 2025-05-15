package net.ritasister.wgrp.api;

import java.lang.reflect.Method;

public final class ApiRegistrationUtil {

    private static final Method REGISTER;
    private static final Method UNREGISTER;

    private ApiRegistrationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    static {
        try {
            REGISTER = WorldGuardRegionProtectProvider.class.getDeclaredMethod("register", WorldGuardRegionProtect.class);
            REGISTER.setAccessible(true);

            UNREGISTER = WorldGuardRegionProtectProvider.class.getDeclaredMethod("unregister");
            UNREGISTER.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void registerProvider(WorldGuardRegionProtect wgrpApi) {
        try {
            REGISTER.invoke(null, wgrpApi);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public static void unregisterProvider() {
        try {
            UNREGISTER.invoke(null);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

}
