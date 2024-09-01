package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "t_category")
public class CategoryDao {
    @Id
    @Column(name = "id_category_key", nullable = false, length = 100)
    private String id;

    @Column(name = "tx_description", length = 256)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<MeterDao> meters = new ArrayList<>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<GradeDao> grades = new ArrayList<>();
}