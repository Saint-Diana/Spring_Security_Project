package com.shen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Shen
 * @date 2022/12/17 20:15
 */
@MapperScan(basePackages = {"com.shen.mapper"})
@SpringBootApplication
public class TokenApplication {
    public static void main(String[] args) {
        SpringApplication.run(TokenApplication.class, args);
    }
}
