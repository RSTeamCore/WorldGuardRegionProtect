package net.rsteamcore.wgrp.rslibs.api.exceptions;

/***
 * The exception for catching an error if the region is not selected.
 */
public class NoSelectionException extends Exception {
    public NoSelectionException() {
        super("Selection not has been made!");
    }
}
