package com.libraryapp.a_e_5_libreria.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.libraryapp.a_e_5_libreria.domain")
@EnableJpaRepositories("com.libraryapp.a_e_5_libreria.repos")
@EnableTransactionManagement
public class DomainConfig {
}
