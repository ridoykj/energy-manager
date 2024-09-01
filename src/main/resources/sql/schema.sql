-- energy_manager.t_area definition

CREATE TABLE `t_area`
(
    `id_area_key`    varchar(100) NOT NULL,
    `tx_city`        varchar(100) NOT NULL,
    `tx_union`       varchar(100) NOT NULL,
    `tx_road`        varchar(100) DEFAULT NULL,
    `tx_address`     varchar(100) DEFAULT NULL,
    `tx_description` varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id_area_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_bill_seq definition

CREATE TABLE `t_bill_seq`
(
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_category definition

CREATE TABLE `t_category`
(
    `id_category_key` varchar(100) NOT NULL,
    `tx_description`  varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id_category_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_designation definition

CREATE TABLE `t_designation`
(
    `id_designation_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
    `tx_description`     varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id_designation_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_grade_seq definition

CREATE TABLE `t_grade_seq`
(
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_user_seq definition

CREATE TABLE `t_user_seq`
(
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_customer definition

CREATE TABLE `t_customer`
(
    `id_customer_key` varchar(255) NOT NULL,
    `tx_address`      varchar(255) DEFAULT NULL,
    `tx_gender`       varchar(255) DEFAULT NULL,
    `tx_mobile`       varchar(255) DEFAULT NULL,
    `tx_name`         varchar(255) DEFAULT NULL,
    `tx_nid`          varchar(255) DEFAULT NULL,
    `is_state`        bit(1)       DEFAULT NULL,
    `id_area_key`     varchar(100) DEFAULT NULL,
    `bt_image`        blob,
    PRIMARY KEY (`id_customer_key`),
    KEY               `FKjgc0fgb59136sf9ebdo3yjnbx` (`id_area_key`),
    CONSTRAINT `FKjgc0fgb59136sf9ebdo3yjnbx` FOREIGN KEY (`id_area_key`) REFERENCES `t_area` (`id_area_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_employee definition

CREATE TABLE `t_employee`
(
    `id_employee_key`    varchar(100) NOT NULL,
    `tx_name`            varchar(100) NOT NULL,
    `tx_nid`             varchar(100) NOT NULL,
    `tx_gender`          varchar(100) NOT NULL,
    `dt_birth_date`      date           DEFAULT NULL,
    `tx_mobile`          varchar(100) NOT NULL,
    `is_state`           bit(1)       NOT NULL,
    `tx_address`         varchar(100)   DEFAULT NULL,
    `flt_salary`         decimal(10, 0) DEFAULT NULL,
    `tx_description`     varchar(256)   DEFAULT NULL,
    `id_designation_key` varchar(100)   DEFAULT NULL,
    `id_area_key`        varchar(100)   DEFAULT NULL,
    `bt_image`           blob,
    PRIMARY KEY (`id_employee_key`),
    KEY                  `FK15xxlu9gejsb5grkim9ls4njc` (`id_designation_key`),
    KEY                  `FK964j1vyasg6n6xaclji6t07rb` (`id_area_key`),
    CONSTRAINT `FK15xxlu9gejsb5grkim9ls4njc` FOREIGN KEY (`id_designation_key`) REFERENCES `t_designation` (`id_designation_key`),
    CONSTRAINT `FK964j1vyasg6n6xaclji6t07rb` FOREIGN KEY (`id_area_key`) REFERENCES `t_area` (`id_area_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_grade definition

CREATE TABLE `t_grade`
(
    `id_grade_key`    bigint         NOT NULL,
    `ct_min_unit`     decimal(10, 0) NOT NULL,
    `ct_max_unit`     decimal(10, 0) NOT NULL,
    `flt_price`       decimal(10, 0) NOT NULL,
    `flt_vat`         decimal(10, 0) NOT NULL,
    `flt_sc`          decimal(10, 0) NOT NULL,
    `flt_sd`          decimal(10, 0) NOT NULL,
    `tx_description`  varchar(256) DEFAULT NULL,
    `id_category_key` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id_grade_key`),
    KEY               `FKmjksuwv7g8awuapb3bn1ur82l` (`id_category_key`),
    CONSTRAINT `FKmjksuwv7g8awuapb3bn1ur82l` FOREIGN KEY (`id_category_key`) REFERENCES `t_category` (`id_category_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_meter definition

CREATE TABLE `t_meter`
(
    `id_meter_key`    varchar(255) NOT NULL,
    `tx_brand`        varchar(255) DEFAULT NULL,
    `tx_description`  varchar(255) DEFAULT NULL,
    `tx_model`        varchar(255) DEFAULT NULL,
    `tx_name`         varchar(255) DEFAULT NULL,
    `dt_purchase`     date         DEFAULT NULL,
    `is_state`        bit(1)       DEFAULT NULL,
    `id_category_key` varchar(100) DEFAULT NULL,
    `id_customer_key` varchar(255) DEFAULT NULL,
    `dt_last_reg`     date         DEFAULT NULL,
    PRIMARY KEY (`id_meter_key`),
    KEY               `FK4a00wilqq15mw4njis49r0q2l` (`id_category_key`),
    KEY               `FKb4gp8jk6bpp7w7owp5f0h0kj1` (`id_customer_key`),
    CONSTRAINT `FK4a00wilqq15mw4njis49r0q2l` FOREIGN KEY (`id_category_key`) REFERENCES `t_category` (`id_category_key`),
    CONSTRAINT `FKb4gp8jk6bpp7w7owp5f0h0kj1` FOREIGN KEY (`id_customer_key`) REFERENCES `t_customer` (`id_customer_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_user definition

CREATE TABLE `t_user`
(
    `id_user_key`     bigint NOT NULL,
    `tx_name`         varchar(255) DEFAULT NULL,
    `is_login`        bit(1)       DEFAULT NULL,
    `tx_pass`         varchar(255) DEFAULT NULL,
    `tx_role`         varchar(255) DEFAULT NULL,
    `base64hash`      varchar(255) DEFAULT NULL,
    `center_key`      int          DEFAULT NULL,
    `center_name`     varchar(255) DEFAULT NULL,
    `counter`         int          DEFAULT NULL,
    `project_key`     int          DEFAULT NULL,
    `template_name`   varchar(255) DEFAULT NULL,
    `id_employee_key` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id_user_key`),
    UNIQUE KEY `UKehu8yiyydkdsaqhf1aqwx0y62` (`id_employee_key`),
    CONSTRAINT `FKir1j8it4sd5fdye855ghvqo76` FOREIGN KEY (`id_employee_key`) REFERENCES `t_employee` (`id_employee_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- energy_manager.t_bill definition

CREATE TABLE `t_bill`
(
    `id_bill_key`     bigint         NOT NULL,
    `ct_year`         int            NOT NULL,
    `ct_month`        int            NOT NULL,
    `ct_value`        decimal(38, 2) DEFAULT NULL,
    `dt_read`         date           DEFAULT NULL,
    `flt_unit`        decimal(10, 0) NOT NULL,
    `is_paid`         bit(1)         DEFAULT NULL,
    `dt_paid`         date           DEFAULT NULL,
    `id_customer_key` varchar(255)   DEFAULT NULL,
    `id_employee_key` varchar(100)   DEFAULT NULL,
    `id_meter_key`    varchar(255)   DEFAULT NULL,
    PRIMARY KEY (`id_bill_key`),
    KEY               `FK381oqu6uuwhln8fim0cijjt67` (`id_customer_key`),
    KEY               `FKe4ru2ufr09ttl11dxbfsh5e7v` (`id_employee_key`),
    KEY               `FK26ygkx2ciwpd249huapk1avul` (`id_meter_key`),
    CONSTRAINT `FK26ygkx2ciwpd249huapk1avul` FOREIGN KEY (`id_meter_key`) REFERENCES `t_meter` (`id_meter_key`),
    CONSTRAINT `FK381oqu6uuwhln8fim0cijjt67` FOREIGN KEY (`id_customer_key`) REFERENCES `t_customer` (`id_customer_key`),
    CONSTRAINT `FKe4ru2ufr09ttl11dxbfsh5e7v` FOREIGN KEY (`id_employee_key`) REFERENCES `t_employee` (`id_employee_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;