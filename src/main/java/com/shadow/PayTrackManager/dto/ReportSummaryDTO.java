package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportSummaryDTO {
    private BigDecimal totalPlanilla;
    private List<DiscountDTO> discounts;
    private List<MoneyDTO> monies;
}
