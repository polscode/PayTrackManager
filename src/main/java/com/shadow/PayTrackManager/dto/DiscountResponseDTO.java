package com.shadow.PayTrackManager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponseDTO {
    private Long id;
    private String name;
    private BigDecimal total;
    private List<BigDecimal> amounts;
}