package net.ritasister.wgrp.handler.abstracts;

import net.ritasister.wgrp.handler.interfaces.Handler;

public abstract class AbstractHandler<V> implements Handler<V> {

    @Override
    public void handle() {}

    @Override
    public void handle(final V v) {}

}
