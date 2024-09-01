package com.itbd.energymanager.dao;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_user")
public class UserDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user_key")
    private Long id;
    private Integer projectKey;
    private String base64Hash;
    private Integer counter;
    private Boolean isLogin;
    private String templateName;
    private Integer centerKey;
    private String centerName;

    @Column(name = "tx_name")
    private String name;

    @Column(name = "tx_pass")
    private String pass;

    @Column(name = "tx_role")
    private String role;

    @OneToOne
    @JoinColumn(name = "id_employee_key")
    private EmployeeDao employee;
}
