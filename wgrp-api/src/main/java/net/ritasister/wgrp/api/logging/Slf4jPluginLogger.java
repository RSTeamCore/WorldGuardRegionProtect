package net.ritasister.wgrp.api.logging;

import org.slf4j.Logger;

public class Slf4jPluginLogger implements PluginLogger {

    private final Logger logger;

    public Slf4jPluginLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void info(String message) {
        this.logger.info(message);
    }

    @Override
    public void warn(String message) {
        this.logger.warn(message);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        this.logger.warn(message, throwable);
    }

    @Override
    public void severe(String message) {
        this.logger.error(message);
    }

    @Override
    public void severe(String message, Throwable throwable) {
        this.logger.error(message, throwable);
    }

}
