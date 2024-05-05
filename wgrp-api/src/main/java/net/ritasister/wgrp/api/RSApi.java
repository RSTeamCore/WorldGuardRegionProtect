package net.ritasister.wgrp.api;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;
import org.jetbrains.annotations.NotNull;

public interface RSApi<E, P, C> {

    boolean isPlayerListenerPermission(@NotNull P player, @NotNull UtilPermissions perm);

    boolean isEntityListenerPermission(@NotNull E entity, @NotNull UtilPermissions perm);

    void notify(P player, String playerName, String senderCommand, String regionName);

    void entityCheck(C cancellable, E entity, @NotNull E checkEntity);
}
