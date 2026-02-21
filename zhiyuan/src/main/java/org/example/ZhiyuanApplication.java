package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// 扫描配置类所在的包
@ComponentScan(basePackages = {
        "org.example.controller",    // 你的TGoodsController所在包
        "org.example.service",       // Service层包（如果有）
        "org.example.mapper",        // Mapper层包
        "org.example.config"         // 配置类所在包
        })
// 扫描Mapper包并指定sqlSessionFactory
@MapperScan(basePackages = "org.example.mapper",
        sqlSessionFactoryRef = "sqlSessionFactory")
@SpringBootApplication
public class ZhiyuanApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZhiyuanApplication.class, args);
    }
}