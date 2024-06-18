package net.ritasister.wgrp.api.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPluginLogger implements PluginLogger {
    private final Logger logger;

    public JavaPluginLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(final String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(final String message) {
        this.logger.warning(message);
    }

    @Override
    public void warn(final String message, final Throwable throwable) {
        this.logger.log(Level.WARNING, message, throwable);
    }

    @Override
    public void severe(final String message) {
        this.logger.severe(message);
    }

    @Override
    public void severe(final String message, final Throwable throwable) {
        this.logger.log(Level.SEVERE, message, throwable);
    }

}
