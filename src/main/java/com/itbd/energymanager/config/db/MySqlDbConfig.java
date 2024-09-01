package com.itbd.energymanager.config.db;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan(basePackages = {"com.itbd.energymanager.dao"})
@EnableJpaRepositories(basePackages = {"com.itbd.energymanager.repos"})
@EnableTransactionManagement
public class MySqlDbConfig {
}
