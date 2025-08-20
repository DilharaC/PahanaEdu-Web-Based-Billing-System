package com.pahanaedu.daotest;

import com.pahanaedu.dao.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testGetConnection_NotNullAndOpen() throws SQLException {
        try (Connection conn = DBConnection.getConnection()) {
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        }
    }

    @Test
    void testGetConnection_ReusesSameInstance() throws SQLException {
        Connection conn1 = DBConnection.getConnection();
        Connection conn2 = DBConnection.getConnection();

        assertSame(conn1, conn2, "DBConnection should return the same instance (Singleton)");
    }

    @Test
    void testGetConnection_AfterClose_ShouldReconnect() throws SQLException {
        Connection conn1 = DBConnection.getConnection();
        conn1.close();

        Connection conn2 = DBConnection.getConnection();
        assertNotNull(conn2, "Should reconnect after closing");
        assertFalse(conn2.isClosed(), "New connection should be open");
    }

  
}
