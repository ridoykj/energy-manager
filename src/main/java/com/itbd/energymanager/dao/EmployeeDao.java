package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "t_employee")
public class EmployeeDao {
    @Id
    @Column(name = "id_employee_key", nullable = false, length = 100)
    private String id;

    @Column(name = "tx_name", nullable = false, length = 100)
    private String name;

    @Column(name = "tx_nid", nullable = false, length = 100)
    private String nid;

    @Column(name = "tx_gender", nullable = false, length = 100)
    private String gender;

    @Column(name = "dt_birth_date")
    private LocalDate birthDate;

    @Column(name = "tx_mobile", nullable = false, length = 100)
    private String mobile;

    @Column(name = "is_state", nullable = false)
    private Boolean isState = false;

    @Column(name = "tx_address", length = 100)
    private String address;

    @Column(name = "flt_salary", precision = 10)
    private BigDecimal salary;

    @Column(name = "tx_description", length = 256)
    private String description;

    @Lob
    @Column(name = "bt_image", columnDefinition = "BLOB")
    private byte[] image;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private UserDao user;

    @ManyToOne
    @JoinColumn(name = "id_area_key")
    private AreaDao area;

    @ManyToOne
    @JoinColumn(name = "id_designation_key")
    private DesignationDao designation;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<BillDao> bills;
}