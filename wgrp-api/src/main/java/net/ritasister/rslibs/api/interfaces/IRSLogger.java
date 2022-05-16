package net.ritasister.rslibs.api.interfaces;

import java.io.File;
import java.util.logging.Logger;

public interface IRSLogger {
    Logger ROOT_LOGGER_NAME = Logger.getLogger("WGRP");

    void info(final String msg);
    void warn(final String msg);
    void error(final String msg);
    void loadConfigMsgSuccess(final File fileName);
    void updateConfigMsgSuccess(final File fileName);

}
