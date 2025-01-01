package com.sis.server.config;

import com.sis.server.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    repositoryBaseClass = BaseRepositoryImpl.class,
    basePackages = "com.sis.server.repository")
public class SisRepositoryConfig {}
