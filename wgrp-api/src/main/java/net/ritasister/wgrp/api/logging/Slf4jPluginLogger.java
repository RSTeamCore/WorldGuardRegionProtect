package net.ritasister.wgrp.api.logging;

import org.slf4j.Logger;

/**
 * Use this class if you need only Slf4 logger.
 */
public class Slf4jPluginLogger implements PluginLogger {

    private final Logger logger;

    public Slf4jPluginLogger(Logger logger) {
        this.logger = logger;
    }

    /**
     * Through info while running plugin.
     */
    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    /**
     * Through warning while running plugin.
     */
    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    /**
     * Through throwable warning while running plugin.
     */
    @Override
    public void warn(String message, Throwable throwable) {
        this.logger.warn(message, throwable);
    }

    /**
     * Through severe error while running plugin.
     */
    @Override
    public void severe(String message) {
        this.logger.error(message);
    }

    /**
     * Through throwable severe error while running plugin.
     */
    @Override
    public void severe(String message, Throwable throwable) {
        this.logger.error(message, throwable);
    }

}
