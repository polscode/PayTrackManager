package com.shadow.PayTrackManager.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name ="tb_report")
@ToString(exclude = {"discounts", "monies"})
public class Report {

    @Id
    private Long id;

    @Column(scale = 2)
    private BigDecimal totalPlanilla;

    @Column(nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Discount>  discounts;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Money> monies;

    @ManyToOne
    @JoinColumn(name = "id_driver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "id_plate")
    private Plate plate;
}
