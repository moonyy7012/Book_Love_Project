package com.ssafy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@ComponentScan({"com.ssafy.core"})
@ComponentScan({"com.ssafy.api"})
@EntityScan("com.ssafy.core")
@EnableJpaRepositories("com.ssafy.core")
public class ApiApplication {
    @PostConstruct
    void startred() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
