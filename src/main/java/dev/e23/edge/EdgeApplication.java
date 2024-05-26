package dev.e23.edge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication // Spring Boot 应用
@EnableWebSecurity // 开启 Spring Security
@EnableJpaAuditing // 用于 JPA 进行自动填充创建时间和更新时间
@EnableTransactionManagement // 用于 JPA 进行事务管理
public class EdgeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EdgeApplication.class, args);
    }
}
