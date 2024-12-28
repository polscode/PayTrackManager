package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@AllArgsConstructor
@Data
public class BasicReportDataDTO {

    private Long id;
    private LocalDate date;
}
