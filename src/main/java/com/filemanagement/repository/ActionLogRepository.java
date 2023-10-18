package com.filemanagement.repository;

import com.filemanagement.entity.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {

}
