package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_designation")
public class DesignationDao {
    @Id
    @Column(name = "id_designation_key", nullable = false, length = 100)
    private String id;

    @Column(name = "tx_description", length = 256)
    private String description;

    @OneToMany(mappedBy = "designation", cascade = CascadeType.ALL)
    private List<EmployeeDao> employees = new ArrayList<>();
}