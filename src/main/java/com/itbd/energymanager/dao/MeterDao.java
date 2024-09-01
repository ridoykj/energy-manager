package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "t_meter")
public class MeterDao {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_meter_key")
    private String id;

    @Column(name = "tx_brand")
    private String brand;


    @Column(name = "tx_description")
    private String description;

    @Column(name = "tx_model")
    private String model;

    @Column(name = "tx_name")
    private String name;

    @Column(name = "dt_purchase")
    private LocalDate purchase;


    @Column(name = "dt_last_reg")
    private LocalDate registration;


    @Column(name = "is_state")
    private Boolean isState;

    @ManyToOne
    @JoinColumn(name = "id_customer_key")
    private CustomerDao customer;

    @ManyToOne
    @JoinColumn(name = "id_category_key")
    private CategoryDao category;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
    private List<BillDao> bills;
}
