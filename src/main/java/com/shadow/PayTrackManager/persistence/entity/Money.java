package com.shadow.PayTrackManager.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_money")
@ToString(exclude = "report")
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 2)
    @Enumerated(EnumType.STRING)
    private Denomination denomination;

    @Column(scale = 2)
    private BigDecimal quantity;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Getter(AccessLevel.NONE)
    private Report report;
}

/*
 * {
 *     "id": null,
 *     "name": "ANULADOS",
 *     "amounts": [
 *         { "value": 50 },
 *         { "value": 100 }
 *     ]
 * }
 */

/*
{
    "monies": [
        { "denomination": "COIN_0_10", "quantity": 5 },
        { "denomination": "BILL_20_00", "quantity": 3 }
    ]
}
{
    "monies": [
        { "denomination": "COIN_0_10", "quantity": 5 },
        { "denomination": "BILL_20_00", "quantity": 3 }
    ],
    "totalMoney": 60.50
}
 */