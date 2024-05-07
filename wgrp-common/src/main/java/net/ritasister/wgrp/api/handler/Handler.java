package net.ritasister.wgrp.api.handler;

public interface Handler<V> {

    default void handle(V v) {}
    default void handle() {}
}
