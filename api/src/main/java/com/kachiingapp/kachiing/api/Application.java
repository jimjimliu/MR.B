package com.kachiingapp.kachiing.api;

import com.kachiingapp.kachiing.data.DataConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude= {SecurityAutoConfiguration.class, DataSourceAutoConfiguration.class }) // 关闭springSecurity中的过滤接口功能
@Import({DataConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
