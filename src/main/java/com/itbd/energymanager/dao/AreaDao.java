package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_area")
public class AreaDao {
    @Id
    @Column(name = "id_area_key", nullable = false, length = 100)
    private String id;

    @Column(name = "tx_city", nullable = false, length = 100)
    private String city;

    @Column(name = "tx_union", nullable = false, length = 100)
    private String union;

    @Column(name = "tx_road", length = 100)
    private String road;

    @Column(name = "tx_address", length = 100)
    private String address;

    @Column(name = "tx_description", length = 256)
    private String description;

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<EmployeeDao> employees = new ArrayList<>();

    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<CustomerDao> customers = new ArrayList<>();
}