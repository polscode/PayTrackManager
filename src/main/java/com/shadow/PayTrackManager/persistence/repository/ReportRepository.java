package com.shadow.PayTrackManager.persistence.repository;

import com.shadow.PayTrackManager.persistence.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ReportRepository extends JpaRepository<Report, Long> {
     Page<Report> findByDate(LocalDate localDate
     );
}
