package com.zhanyd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zhanyd.mapper")
public class WebApp {
	public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
    }
}
