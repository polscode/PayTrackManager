package com.shadow.PayTrackManager.service;

import com.shadow.PayTrackManager.dto.*;
import com.shadow.PayTrackManager.persistence.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    CalculatedDataDTO calculateReport(ReportSummaryDTO reportSummaryDTO);
    Page<ReportResponseDTO> findAll(Pageable pageable);
    Page<Report> findById(Pageable pageable);
    Page<Report> findByDate(Pageable pageable);
    Report save(SaveReportDTO saveReportDTO);
    Report update(UpdateReportDTO updateReportDTO);
}
