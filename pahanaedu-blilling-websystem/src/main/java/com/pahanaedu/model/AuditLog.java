package com.pahanaedu.model;

import java.time.LocalDateTime;

public class AuditLog {
    private int id;
    private String action;
    private String performedBy;
    private String targetEntity;
    private int targetId;
    private String details;
    private LocalDateTime timestamp;

    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public String getPerformedBy() { return performedBy; }
    public void setPerformedBy(String performedBy) { this.performedBy = performedBy; }

    public String getTargetEntity() { return targetEntity; }
    public void setTargetEntity(String targetEntity) { this.targetEntity = targetEntity; }

    public int getTargetId() { return targetId; }
    public void setTargetId(int targetId) { this.targetId = targetId; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}