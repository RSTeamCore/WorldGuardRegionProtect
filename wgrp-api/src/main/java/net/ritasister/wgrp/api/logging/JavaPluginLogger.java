package net.ritasister.wgrp.api.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Use this class if you need only java plugin logger.
 */
public class JavaPluginLogger implements PluginLogger {

    private final Logger logger;

    public JavaPluginLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Through info while running plugin.
     */
    @Override
    public void info(final String message) {
        this.logger.info(message);
    }

    /**
     * Through warning while running plugin.
     */
    @Override
    public void warn(final String message) {
        this.logger.warning(message);
    }

    /**
     * Through throwable warning while running plugin.
     */
    @Override
    public void warn(final String message, final Throwable throwable) {
        this.logger.log(Level.WARNING, message, throwable);
    }

    /**
     * Through severe error while running plugin.
     */
    @Override
    public void severe(final String message) {
        this.logger.severe(message);
    }

    /**
     * Through throwable severe error while running plugin.
     */
    @Override
    public void severe(final String message, final Throwable throwable) {
        this.logger.log(Level.SEVERE, message, throwable);
    }

}
