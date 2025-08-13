package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.pahanaedu.model.AuditLog;

public class AuditLogDAO {

    public void addAuditLog(AuditLog log, Connection conn) throws SQLException {
        String sql = "INSERT INTO audit_log (action, performed_by, target_entity, target_id, details, timestamp) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, log.getAction());
            ps.setString(2, log.getPerformedBy());
            ps.setString(3, log.getTargetEntity());
            ps.setInt(4, log.getTargetId());
            ps.setString(5, log.getDetails());
            ps.setObject(6, log.getTimestamp());
            ps.executeUpdate();
        }
    }
}