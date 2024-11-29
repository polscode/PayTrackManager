package com.shadow.PayTrackManager.service;

import com.shadow.PayTrackManager.dto.ReportRequestDTO;
import com.shadow.PayTrackManager.dto.ReportResponseDTO;
import com.shadow.PayTrackManager.persistence.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    ReportResponseDTO calculateReport(ReportRequestDTO reportRequestDTO);
    Page<ReportResponseDTO> findAll(Pageable pageable);
    Page<Report> findById(Pageable pageable);
    Page<Report> findByDate(Pageable pageable);
    Report save(ReportRequestDTO reportRequestDTO);
}
