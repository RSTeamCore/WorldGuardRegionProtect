package net.ritasister.wgrp.rslibs.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.CartographyInventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PlayerCartographyEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean cancelled;
    private final CartographyInventory cartographyInventory;

    public PlayerCartographyEvent(@NotNull final Player player, final CartographyInventory cartographyInventory) {
        super(player);
        this.cartographyInventory = cartographyInventory;
    }

    @NotNull
    public CartographyInventory getCartographyInventory() {
        return cartographyInventory;
    }

    public ItemStack getPaper() {
        return cartographyInventory.getItem(0);
    }

    public ItemStack getMap() {
        return cartographyInventory.getItem(0);
    }

    public ItemStack getFiledMap() {
        return cartographyInventory.getItem(0);
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
