package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalculatedDataDTO {
    private BigDecimal totalDiscount;
    private BigDecimal totalMoney;
    private BigDecimal totalPayable;
    private BigDecimal balance;
}
