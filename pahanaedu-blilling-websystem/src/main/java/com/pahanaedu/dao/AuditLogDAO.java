package com.pahanaedu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
    public List<AuditLog> getAllLogs(Connection conn) throws SQLException {
        List<AuditLog> logs = new ArrayList<>();
        String sql = "SELECT log_id, action, performed_by, target_entity, target_id, details, timestamp FROM audit_log";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AuditLog log = new AuditLog();
                log.setId(rs.getInt("log_id")); // match DB column
                log.setAction(rs.getString("action"));
                log.setPerformedBy(rs.getString("performed_by"));
                log.setTargetEntity(rs.getString("target_entity"));
                log.setTargetId(rs.getInt("target_id"));
                log.setDetails(rs.getString("details"));
                log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime()); // convert SQL timestamp to LocalDateTime
                logs.add(log);
            }
        }

        return logs;
    }
    public List<AuditLog> searchLogs(Connection conn, String search, String category) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT log_id, action, performed_by, target_entity, target_id, details, timestamp FROM audit_log WHERE 1=1 ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND (action LIKE ? OR performed_by LIKE ?) ");
        }

        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND target_entity = ? ");
        }

        sql.append("ORDER BY timestamp DESC");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int index = 1;

            if (search != null && !search.trim().isEmpty()) {
                String likeSearch = "%" + search + "%";
                ps.setString(index++, likeSearch);
                ps.setString(index++, likeSearch);
            }

            if (category != null && !category.trim().isEmpty()) {
                ps.setString(index++, category);
            }

            try (ResultSet rs = ps.executeQuery()) {
                List<AuditLog> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(mapRowToAuditLog(rs));
                }
                return list;
            }
        }
    }
    private AuditLog mapRowToAuditLog(ResultSet rs) throws SQLException {
        AuditLog log = new AuditLog();
        log.setId(rs.getInt("log_id"));                 // <-- must match DB
        log.setAction(rs.getString("action"));
        log.setPerformedBy(rs.getString("performed_by"));
        log.setTargetEntity(rs.getString("target_entity"));
        log.setTargetId(rs.getInt("target_id"));
        log.setDetails(rs.getString("details"));
        log.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
        return log;
    }
}