package com.whut;

import cn.dev33.satoken.SaManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import cn.dev33.satoken.stp.StpUtil;

@SpringBootApplication
public class SoftJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftJobApplication.class, args);
        System.out.println("启动成功，Sa-Token 配置如下：" + SaManager.getConfig());
    }

}
