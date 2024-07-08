package com.whut;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.dev33.satoken.stp.StpUtil;

@SpringBootApplication
public class SoftJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftJobApplication.class, args);
    }

}
