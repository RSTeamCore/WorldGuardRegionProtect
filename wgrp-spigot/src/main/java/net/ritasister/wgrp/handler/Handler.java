package net.ritasister.wgrp.handler;

public interface Handler<V> {

    default void handle(V v) {}
    default void handle() {}
}
