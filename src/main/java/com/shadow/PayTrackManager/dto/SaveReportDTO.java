package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveReportDTO {
    private Long id;
    private BigDecimal totalPlanilla;
    private List<DiscountRequestDTO> discounts;
    private List<MoneyRequestDTO> monies;
    private Long driverId;
    private Long plateId;
}
