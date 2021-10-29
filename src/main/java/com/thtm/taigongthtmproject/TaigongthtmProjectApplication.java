package com.thtm.taigongthtmproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@MapperScan("com.thtm.taigongthtmproject.mapper.*")
@SpringBootApplication
public class TaigongthtmProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaigongthtmProjectApplication.class, args);
        System.out.println("太原太工天昊土木工程检测有限公司项目启动成功-----");
    }

}
