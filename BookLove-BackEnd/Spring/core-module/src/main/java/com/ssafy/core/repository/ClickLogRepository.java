package com.ssafy.core.repository;

import com.ssafy.core.entity.ClickLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClickLogRepository extends JpaRepository<ClickLog, Long>, ClickLogRepoCommon {
}
