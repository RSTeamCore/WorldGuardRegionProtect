package net.ritasister.wgrp.rslibs.exceptions;

import java.io.Serial;

/**
 * The exception for catching an error if the region is not selected.
 */
public class NoSelectionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1;

    public NoSelectionException() {
        super("Selection not has been made!");
    }

}
