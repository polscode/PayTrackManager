package com.shadow.PayTrackManager.persistence.repository;

import com.shadow.PayTrackManager.persistence.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReportRepository extends JpaRepository<Report, Long> {
     @Query("SELECT r FROM Report r WHERE r.date = :date")
     Page<Report> findByDate(@Param("date")LocalDate date, Pageable pageable);
     Page<Report> findById(Long id, Pageable pageable);
}
