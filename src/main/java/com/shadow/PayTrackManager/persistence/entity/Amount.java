package com.shadow.PayTrackManager.persistence.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
//@Table(name = "tb_amount")
public class Amount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    @Getter(AccessLevel.NONE)
    private Discount discount;
}
