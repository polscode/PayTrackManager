package com.shadow.PayTrackManager.controller;

import com.shadow.PayTrackManager.dto.BasicReportDataDTO;
import com.shadow.PayTrackManager.dto.SaveReportDTO;
import com.shadow.PayTrackManager.dto.UpdateReportDTO;
import com.shadow.PayTrackManager.persistence.entity.Report;
import com.shadow.PayTrackManager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public ResponseEntity<Page<BasicReportDataDTO>> findAll(Pageable pageable) {
        Page<BasicReportDataDTO> reportPage = reportService.findAll(pageable);
        if (reportPage.hasContent()) {
            return ResponseEntity.ok(reportPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BasicReportDataDTO>> searchReports(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String date,
            Pageable pageable) {
        if (id != null) {
            Page<BasicReportDataDTO> basicReportDataDTO = reportService.findById(id , pageable);
            return ResponseEntity.ok(basicReportDataDTO);
        }
        if (date != null) {
            LocalDate parseDate = LocalDate.parse(date);
            Page<BasicReportDataDTO> basicReportDataDTO = reportService.findByDate(parseDate, pageable);
            return ResponseEntity.ok(basicReportDataDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping
    public ResponseEntity<Report> createOne(@RequestBody @Validated SaveReportDTO saveReportDTO) {
        Report report = reportService.save(saveReportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PutMapping
    public ResponseEntity<Report> update(@RequestBody @Validated UpdateReportDTO updateReportDTO) {
        Report report = reportService.update(updateReportDTO);
        return ResponseEntity.ok(report);
    }
}
