package com.shadow.PayTrackManager.persistence.entity;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Denomination {
    COIN_0_10(new BigDecimal("0.10")),
    COIN_0_20(new BigDecimal("0.20")),
    COIN_0_50(new BigDecimal("0.50")),
    COIN_1_00(new BigDecimal("1.00")),
    COIN_2_00(new BigDecimal("2.00")),
    COIN_5_00(new BigDecimal("5.00"));

    private final BigDecimal value;

    Denomination(BigDecimal value) {
        this.value = value;
    }
}

