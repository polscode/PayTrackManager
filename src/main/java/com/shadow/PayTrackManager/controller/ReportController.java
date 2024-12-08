package com.shadow.PayTrackManager.controller;

import com.shadow.PayTrackManager.dto.SaveReportDTO;
import com.shadow.PayTrackManager.persistence.entity.Report;
import com.shadow.PayTrackManager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;
/*
    @GetMapping
    public ResponseEntity<Page<Report>> findAll(Pageable pageable) {
        return null;
    }
*/
    @PostMapping
    public ResponseEntity<Report> createOne(@RequestBody @Validated SaveReportDTO saveReportDTO) {
        Report report = reportService.save(saveReportDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }
}
