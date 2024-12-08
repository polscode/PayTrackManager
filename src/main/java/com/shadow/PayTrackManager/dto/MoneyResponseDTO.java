package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyResponseDTO {

    private Long id;
    private BigDecimal value;
    private BigDecimal quantity;

}
