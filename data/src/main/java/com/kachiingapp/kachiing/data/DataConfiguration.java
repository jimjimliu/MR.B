package com.kachiingapp.kachiing.data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ComponentScan
@Configuration
@MapperScan("com.kachiingapp.kachiing.data.dao")
public class DataConfiguration {
//    @Bean(name = "mysqlDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.mysql")
//    public DataSource mysqlDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean(name = "oracleDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.oracle")
//    public DataSource oracleDataSource() {
//        return DataSourceBuilder.create().build();
//    }
}
