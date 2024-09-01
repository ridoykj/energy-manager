package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "t_grade")
public class GradeDao {
    @Id
    @Column(name = "id_grade_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ct_min_unit", nullable = false, precision = 10)
    private BigDecimal minUnit;

    @Column(name = "ct_max_unit", nullable = false, precision = 10)
    private BigDecimal maxUnit;

    @Column(name = "flt_price", nullable = false, precision = 10)
    private BigDecimal rate;

    @Column(name = "flt_vat", nullable = false, precision = 10)
    private BigDecimal vat;

    @Column(name = "flt_sc", nullable = false, precision = 10)
    private BigDecimal sc;

    @Column(name = "flt_sd", nullable = false, precision = 10)
    private BigDecimal sd;

    @Column(name = "tx_description", length = 256)
    private String description;

    @ManyToOne
    @JoinColumn(name = "id_category_key")
    private CategoryDao category;
}