package net.ritasister.wgrp.rslibs.api.exceptions;

public class UnknownRegionException extends Exception {
    public UnknownRegionException() {
        super("Region does not exists!");
    }
}
