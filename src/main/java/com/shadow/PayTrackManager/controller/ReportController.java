package com.shadow.PayTrackManager.controller;

import com.shadow.PayTrackManager.dto.SaveReportDTO;
import com.shadow.PayTrackManager.dto.UpdateReportDTO;
import com.shadow.PayTrackManager.persistence.entity.Report;
import com.shadow.PayTrackManager.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping
    public ResponseEntity<Report> update(@RequestBody @Validated UpdateReportDTO updateReportDTO) {
        System.out.println("ingreso al controlador report");
        System.out.println(updateReportDTO);
        Report report = reportService.update(updateReportDTO);
        return ResponseEntity.ok(report);
    }
}
