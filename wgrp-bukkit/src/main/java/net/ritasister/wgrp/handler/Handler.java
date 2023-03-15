package net.ritasister.wgrp.handler;

public interface Handler<V> {

    void handle(V v);
    void handle();
}
