package net.ritasister.wgrp.api;

import net.ritasister.wgrp.rslibs.permissions.UtilPermissions;

public interface RSApi<E, P, C> {

    boolean isPlayerListenerPermission(P player, UtilPermissions perm);

    boolean isEntityListenerPermission(E entity, UtilPermissions perm);

    void notify(P player, String playerName, String senderCommand, String regionName);

    void entityCheck(C cancellable, E entity, E checkEntity);
}
