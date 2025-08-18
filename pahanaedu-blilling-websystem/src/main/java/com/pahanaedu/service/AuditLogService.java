package com.pahanaedu.service;

import java.sql.Connection;
import java.sql.SQLException;
import com.pahanaedu.dao.AuditLogDAO;
import com.pahanaedu.dao.DBConnectionFactory;
import com.pahanaedu.model.AuditLog;

public class AuditLogService {

    private static AuditLogService instance;
    private AuditLogDAO dao;

    private AuditLogService() {
        // ✅ Create a normal DAO instance (not Singleton)
        dao = new AuditLogDAO();
    }

    // ✅ Thread-safe Singleton for Service layer (this can stay)
    public static AuditLogService getInstance() {
        if (instance == null) {
            synchronized (AuditLogService.class) {
                if (instance == null) {
                    instance = new AuditLogService();
                }
            }
        }
        return instance;
    }

    // Add a log entry
    public void logAction(AuditLog log) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            dao.addAuditLog(log, conn);
        }
    }
}
