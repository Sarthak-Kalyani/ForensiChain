package com.sdcems.sdcems.repository;

import com.sdcems.sdcems.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
}