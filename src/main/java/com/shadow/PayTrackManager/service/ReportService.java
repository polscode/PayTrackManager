package com.shadow.PayTrackManager.service;

import com.shadow.PayTrackManager.dto.*;
import com.shadow.PayTrackManager.persistence.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ReportService {
    CalculatedDataDTO calculateReport(ReportSummaryDTO reportSummaryDTO);
    Page<BasicReportDataDTO> findAll(Pageable pageable);
    Page<BasicReportDataDTO> findById(Long id, Pageable pageable);
    Page<BasicReportDataDTO> findByDate(LocalDate date, Pageable pageable);
    Report save(SaveReportDTO saveReportDTO);
    Report update(UpdateReportDTO updateReportDTO);
}
