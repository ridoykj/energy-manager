package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "t_bill")
public class BillDao {
    @Id
    @Column(name = "id_bill_key", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ct_year", nullable = false)
    private Integer year;

    @Column(name = "ct_month", nullable = false)
    private Integer month;

    @Column(name = "flt_unit", nullable = false, precision = 10)
    private BigDecimal unit;

    @Column(name = "ct_value")
    private BigDecimal value;

    @Column(name = "dt_read")
    private LocalDate read;

    @Column(name = "dt_paid")
    private LocalDate paidDate;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @ManyToOne
    @JoinColumn(name = "id_employee_key")
    private EmployeeDao employee;

    @ManyToOne
    @JoinColumn(name = "id_customer_key")
    private CustomerDao customer;

    @ManyToOne
    @JoinColumn(name = "id_meter_key")
    private MeterDao meter;
}