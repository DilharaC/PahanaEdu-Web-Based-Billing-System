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
        // Use the Singleton instance of DAO
        dao = AuditLogDAO.getInstance();
    }

    // Double-checked locking for thread-safe singleton
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

    // Log action using singleton connection from DBConnectionFactory
    public void logAction(AuditLog log) throws SQLException {
        try (Connection conn = DBConnectionFactory.getConnection()) {
            dao.addAuditLog(log, conn);
        }
    }
}