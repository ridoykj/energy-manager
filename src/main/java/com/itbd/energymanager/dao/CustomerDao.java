package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "t_customer")
public class CustomerDao {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_customer_key")
    private String id;

    @Column(name = "tx_address")
    private String address;

    @Column(name = "tx_gender")
    private String gender;

    @Column(name = "tx_mobile")
    private String mobile;

    @Column(name = "tx_name")
    private String name;

    @Column(name = "tx_nid")
    private String nid;

    @Column(name = "is_state")
    private Boolean isState;

    @Lob
    @Column(name = "bt_image", columnDefinition = "BLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "id_area_key")
    private AreaDao area;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<BillDao> bills;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<MeterDao> roles = new ArrayList<>();
}
