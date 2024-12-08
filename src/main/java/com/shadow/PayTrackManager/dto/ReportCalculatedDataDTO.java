package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportCalculatedDataDTO {
//    private Long id;
//    private BigDecimal totalPlanilla;
    private BigDecimal totalDiscount;
    private BigDecimal totalMoney;
    private BigDecimal totalPayable;
    private BigDecimal balance;
//    private List<DiscountResponseDTO> discounts;
//    private List<MoneyResponseDTO> monies;
}
