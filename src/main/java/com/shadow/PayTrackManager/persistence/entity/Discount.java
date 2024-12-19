package com.shadow.PayTrackManager.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_discount")
@ToString(exclude = "report")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    @ManyToOne
    @JoinColumn(name = "report_id")
    @Getter(AccessLevel.NONE)
    private Report report;


    @Column(scale = 2)
    private List<BigDecimal> amounts;
}
