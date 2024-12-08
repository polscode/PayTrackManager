package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyRequestDTO {
    private String value;
    private BigDecimal quantity;
}