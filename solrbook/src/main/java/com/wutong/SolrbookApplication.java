package com.wutong;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan("com.wutong.mapper")
public class SolrbookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolrbookApplication.class, args);
    }

}
