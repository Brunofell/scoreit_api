package com.scoreit.scoreit.repository;

import com.scoreit.scoreit.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
