package net.ritasister.wgrp.handler.interfaces;

public interface Handler<V> {

    void handle(V v);
    void handle();
}
