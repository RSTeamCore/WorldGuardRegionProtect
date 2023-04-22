package net.ritasister.wgrp.rslibs.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.GrindstoneInventory;
import org.jetbrains.annotations.NotNull;

public class PlayerGrindstoneEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private final GrindstoneInventory grindstoneInventory;

    public PlayerGrindstoneEvent(@NotNull final Player player, final GrindstoneInventory grindstoneInventory) {
        super(player);
        this.grindstoneInventory = grindstoneInventory;
    }

    @NotNull
    public GrindstoneInventory getGrindstoneInventory() {
        return grindstoneInventory;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
