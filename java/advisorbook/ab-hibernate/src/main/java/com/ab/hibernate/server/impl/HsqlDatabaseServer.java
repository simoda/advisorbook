package com.ab.hibernate.server.impl;

import javax.annotation.PreDestroy;

import org.hsqldb.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The database server used for testing
 * 
 * @author ramsokup
 */
public class HsqlDatabaseServer {
    private static final Logger log = LoggerFactory.getLogger(HsqlDatabaseServer.class);
    public static final String DEFAULT_DB_NAME = "testdb";

    private Server server;
    private final int port;
    private boolean initialized = false;
    private final String databaseName;
    private final boolean silent;

    public HsqlDatabaseServer(final String dbName, final int port, boolean silent) {
        this.port = port;
        this.databaseName = dbName;
        this.silent = silent;
    }

    public HsqlDatabaseServer(final String dbName, final int port) {
        this(dbName, port, true);
    }

    public void init() {
        log.info("Initializing db server with db {} on port ", databaseName, port);
        initialized = false;
        server = new Server();
        if (silent) {
            server.setLogWriter(null);
            server.setSilent(true);
            log.info("Silencing db server with db {} on port ", databaseName, port);
        }
        server.setPort(port);
        server.setDatabaseName(0, databaseName);
        server.setDatabasePath(0, "mem:" + databaseName + ";sql.enforce_strict_size=true");
        initialized = true;
        log.info("Initialized db server with db {} om port {}", databaseName, port);
    }

    public void start() {
        try {
            log.info("Starting the database server");
            if (!initialized) {
                init();
            }
            server.start();
            log.info("Started the database server");
        }
        catch (Exception e) {
            log.warn("Database server already running");
        }
    }

    @PreDestroy
    public void stop() {
        log.info("Stopping the database server");
        server.stop();
        initialized = false;
    }

}
