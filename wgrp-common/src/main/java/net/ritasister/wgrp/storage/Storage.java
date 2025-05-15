package net.ritasister.wgrp.storage;

import java.sql.Connection;
import java.sql.SQLException;

public interface Storage {

    Connection getConnection() throws SQLException;

    void setup();

    void configureConnection(String connectionUrl, String username, String password);

    void closeConnection();

    boolean isConnectionActive();

}
